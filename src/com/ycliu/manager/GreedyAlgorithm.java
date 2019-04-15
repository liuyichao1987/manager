package com.ycliu.manager;

import com.ycliu.beans.Conference;
import com.ycliu.beans.Talk;
import com.ycliu.beans.Trace;
import com.ycliu.interfaces.IManager;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 贪心算法
 * @author liuyichao
 * @since 1.0
 */
public class GreedyAlgorithm implements IManager {

    /**
     * trace 排序，按剩余时长从小到大
     */
    private Comparator<Trace> traceComparator = new Comparator<Trace>() {

        @Override
        public int compare(Trace o1, Trace o2) {
            return o1.getRemainTime() - o2.getRemainTime();
        }
    };

    @Override
    public void arrange(List<Talk> talkList, Conference conference) {
        LinkedList<Talk> talksToArrange = new LinkedList<Talk>(talkList);
        //talk 排序， 按时长从大到小
        Collections.sort(talksToArrange, new Comparator<Talk>() {

            @Override
            public int compare(Talk o1, Talk o2) {
                return o2.getDuration() - o1.getDuration();
            }
        });
        //使用贪心算法逻辑，逐个安排
        boolean calcResult = true;
        while (!talksToArrange.isEmpty()) {
            Talk talk = talksToArrange.pollFirst();
            Trace arrangedTrace = arrange(talk, conference.getTraceList());
            if (arrangedTrace == null) {
                calcResult = false;
                break;
            }
        }
        Collections.sort(conference.getTraceList(), new Comparator<Trace>() {

            @Override
            public int compare(Trace o1, Trace o2) {
                if (o1.getTraceId() == o2.getTraceId()) {
                    return o1.getPeriod().ordinal() - o2.getPeriod().ordinal();
                }
                return o1.getTraceId() - o2.getTraceId();
            }
        });

        if (calcResult) {
            System.out.println("计算成功！");
        } else {
            System.out.println("计算失败，有演讲未安排！");
        }
    }

    private Trace arrange(Talk talk, List<Trace> traceList) {
        Collections.sort(traceList, traceComparator);
        Trace lastTrace = traceList.get(traceList.size() - 1);
        if (lastTrace.getRemainTime() == talk.getDuration()) {
            lastTrace.addTalk(talk);
            return lastTrace;
        } else if (lastTrace.getRemainTime() < talk.getDuration()) {
            //返回null表示计算失败
            return null;
        }
        for (Trace trace : traceList) {
            if (trace.getRemainTime() == talk.getDuration()) {
                trace.addTalk(talk);
                return trace;
            } else if (trace.getRemainTime() > talk.getDuration()) {
                break;
            }
        }
        lastTrace.addTalk(talk);
        return lastTrace;
    }
}
