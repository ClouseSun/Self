package com.abition.self;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetServerTimeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by KlousesSun on 16/4/9.
 */
public class Plan implements Comparable<Plan> {
    private PlanTable plan;
    private String title;
    private int type;
    private int steak;
    private int target;
    private Status status;

    @Override
    public int compareTo(Plan another) {
        return this.status.ordinal() - another.status.ordinal();
    }

    public enum Status {
        PROCESSING_UNCHECKED, PROCESSING_CHECKED, FINISHED, FAILED
    }

    static public Map<Integer, Integer> themeStyle;

    static {
        themeStyle = new HashMap<>();
        themeStyle.put(R.drawable.spring, 0xFF009688);
        themeStyle.put(R.drawable.autumn, 0xFFEF6C00);
        themeStyle.put(R.drawable.winter, 0xFF1E88E5);
        themeStyle.put(R.drawable.night, 0xFF455A64);
        themeStyle.put(R.drawable.love, 0xFFE91E63);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public int getTarget() {
        return target;
    }

    public int getType() {
        return type;
    }

    public int getSteak() {
        return steak;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Plan(String title, int type, Date dateFrom, Date dateTo, Context context) {
        this.title = title;
        this.type = type;
        this.status = Status.PROCESSING_UNCHECKED;

        BmobDate bmobDateFrom = new BmobDate(dateFrom);
        BmobDate bmobDateTo = new BmobDate(dateTo);
        BmobUser user = BmobUser.getCurrentUser(context);
        int statusNum = Status.PROCESSING_UNCHECKED.ordinal();
        PlanTable plan = new PlanTable(title, bmobDateFrom, bmobDateTo, user.getObjectId(), type, statusNum);
        this.plan = plan;
        plan.save(context, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("error:", s);
            }
        });

        this.steak = 0;
        this.target = (int) (dateTo.getTime() - dateFrom.getTime()) / (60 * 1000) % 60 + 1;
    }

    public Plan(PlanTable plan) {
        this.plan = plan;
        this.title = plan.getTitle();
        this.type = plan.getType();
        this.status = Status.values()[plan.getStatus()];

        Date dateFrom = null, dateTo = null, datePersist = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFrom = sdf.parse(plan.getDateFrom().getDate());
            dateTo = sdf.parse(plan.getDateTo().getDate());
            datePersist = sdf.parse(plan.getDatePersist().getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.steak = (int) ((datePersist.getTime() - dateFrom.getTime()) / (24 * 60 * 60 * 1000)) + 1;
        this.target = (int) ((dateTo.getTime() - dateFrom.getTime()) / (24 * 60 * 60 * 1000)) + 1;
    }

    static public void getUserPlan(final Context context, final List<Plan> planList) {
        BmobUser user = BmobUser.getCurrentUser(context);
        BmobQuery<PlanTable> query = new BmobQuery<PlanTable>();
        query.addWhereEqualTo("user_id", user.getObjectId());
        query.setLimit(50);

        query.findObjects(context, new FindListener<PlanTable>() {
            @Override
            public void onSuccess(final List<PlanTable> object) {
                Bmob.getServerTime(context, new GetServerTimeListener() {
                    @Override
                    public void onSuccess(long time) {
                        Date nowDate = new Date(time * 1000L);
                        for (PlanTable planData : object) {
                            Plan plan = new Plan(planData);
                            Date datePersist = null;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                datePersist = sdf.parse(plan.plan.getDatePersist().getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int interval = (int)(nowDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (datePersist.getTime() / (24 * 60 * 60 * 1000));
                            if(interval >= 1 && plan.getStatus()!=Status.FAILED){
                                plan.setStatus(Status.FAILED);
                                plan.failPlan(context);
                            }
                            planList.add(plan);
                        }
                        PlanFragment.getInstance().refreshList(planList);
                        AchivFragment.getInstance().refreshList(planList);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }

            @Override
            public void onError(int code, String msg) {
                Log.i("error", "读取计划失败！");
            }
        });
    }

    public void checkPlan(final Context context) {
        Bmob.getServerTime(context, new GetServerTimeListener() {
            @Override
            public void onSuccess(long time) {
                Date nowDate = new Date(time * 1000L);
                PlanTable newPlan = new PlanTable();
                newPlan.setDatePersist(new BmobDate(nowDate));
                plan.setDatePersist(new BmobDate(nowDate));
                Date dateFrom = null, datePersist = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateFrom = sdf.parse(plan.getDateFrom().getDate());
                    datePersist = sdf.parse(plan.getDatePersist().getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                steak = (int) ((datePersist.getTime() - dateFrom.getTime()) / (24 * 60 * 60 * 1000)) + 1;
                if (target == steak) {
                    newPlan.setStatus(Status.FINISHED.ordinal());
                } else {
                    newPlan.setStatus(Status.PROCESSING_CHECKED.ordinal());
                }
                newPlan.update(context, plan.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("bmob", "更新成功");
                        PlanFragment.getInstance().refresh();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("bmob", s);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.i("bmob", "获取服务器时间失败:" + msg);
            }
        });
    }

    public synchronized boolean canCheck(Context context) {

        int status = plan.getStatus();
        int x = Status.PROCESSING_UNCHECKED.ordinal();
        boolean success = status - x == 0;
        return success;
    }

    public void deletePlan(Context context) {
        PlanTable planTable = new PlanTable();
        planTable.setObjectId(plan.getObjectId());
        planTable.delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {
                PlanFragment.getInstance().refresh();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("bmob", "删除失败：" + s);
            }
        });
    }

    public void failPlan(Context context){
        PlanTable planTable = new PlanTable();
        planTable.setObjectId(plan.getObjectId());
        planTable.setStatus(Status.FAILED.ordinal());
        planTable.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                PlanFragment.getInstance().refresh();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("bmob", "删除失败：" + s);
            }
        });
    }
}
