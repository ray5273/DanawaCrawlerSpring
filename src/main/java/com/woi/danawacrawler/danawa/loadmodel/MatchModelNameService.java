package com.woi.danawacrawler.danawa.loadmodel;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchModelNameService {

    public List<MatchModelName> getMatchModelName();
}
