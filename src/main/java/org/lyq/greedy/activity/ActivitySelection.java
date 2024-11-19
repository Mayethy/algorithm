package org.lyq.greedy.activity;

import java.util.Arrays;
import java.util.Comparator;

public class ActivitySelection {
    static class Activity {
        int id;
        int start;
        int finish;

        public Activity(int id, int start, int finish) {
            this.id = id;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public String toString() {
            return "Activity " + id + " (Start: " + start + ", Finish: " + finish + ")";
        }
    }

    public static Activity[] selectActivities(Activity[] activities) {
        // 按结束时间升序排序
        Arrays.sort(activities, Comparator.comparingInt(a -> a.finish));

        // 选择第一个活动
        int n = activities.length;
        Activity[] selectedActivities = new Activity[n];
        int count = 0;
        selectedActivities[count++] = activities[0];

        // 选择后续活动
        int lastFinishTime = activities[0].finish;
        for (int i = 1; i < n; i++) {
            if (activities[i].start >= lastFinishTime) {
                selectedActivities[count++] = activities[i];
                lastFinishTime = activities[i].finish;
            }
        }

        // 返回实际选择的活动
        return Arrays.copyOf(selectedActivities, count);
    }

    public static void main(String[] args) {
        Activity[] activities = {
                new Activity(1, 1, 4),
                new Activity(2, 3, 5),
                new Activity(3, 0, 6),
                new Activity(4, 5, 7),
                new Activity(5, 3, 8),
                new Activity(6, 5, 9),
                new Activity(7, 6, 10),
                new Activity(8, 8, 11),
                new Activity(9, 8, 12),
                new Activity(10, 2, 13),
                new Activity(11, 12, 14)
        };

        Activity[] selectedActivities = selectActivities(activities);
        System.out.println("Selected Activities:");
        for (Activity activity : selectedActivities) {
            System.out.println(activity);
        }
    }
}
