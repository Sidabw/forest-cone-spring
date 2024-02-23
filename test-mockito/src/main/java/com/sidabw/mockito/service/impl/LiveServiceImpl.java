package com.sidabw.mockito.service.impl;

import com.sidabw.mockito.entity.Live;
import com.sidabw.mockito.repository.LiveRepository;
import com.sidabw.mockito.service.ILiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shaogz
 */
@Service
public class LiveServiceImpl implements ILiveService {

    private LiveRepository liveRepository;

    @Override
    public Integer createLiveByRoomId(Integer roomId) {
        Live live = new Live();
        live.setRoomId(roomId);
        live.setUserId(123);
        live.setStatus(123);
        live.setRecordVideoId(123);
        live.setRecordVideoStatus(123);
        live.setTemplateType(123);
        live.setSourceNodeId(123);
        live.setSourceType(123);

        liveRepository.save(live);
        return live.getId();
    }

    @Autowired
    public void setLiveRepository(LiveRepository liveRepository) {
        this.liveRepository = liveRepository;
    }
}
