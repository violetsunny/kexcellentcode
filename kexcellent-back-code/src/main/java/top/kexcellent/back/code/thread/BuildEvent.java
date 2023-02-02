/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import com.lmax.disruptor.EventFactory;

import java.util.List;

/**
 * 
 * @author xy23087
 * @version $Id: BuildEvent.java, v 0.1 2017年2月28日 上午9:53:19 xy23087 Exp $
 */
public class BuildEvent {
    /**
     * 行程信息
     */
    private List<String>                    itinerary;
    /**
     * 预订人信息
     */
    private Long                         bookPerson;
    /**
     * 添加字段注释.
     */
    public static final EventFactory<BuildEvent> EVENT_FACTORY = new EventFactory<BuildEvent>() {
        @Override
        public BuildEvent newInstance() {
            return new BuildEvent();
        }
    };

    public List<String> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<String> itinerary) {
        this.itinerary = itinerary;
    }

    public Long getBookPerson() {
        return bookPerson;
    }

    public void setBookPerson(Long bookPerson) {
        this.bookPerson = bookPerson;
    }
}
