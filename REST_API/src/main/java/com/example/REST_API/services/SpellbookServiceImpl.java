package com.example.REST_API.services;

import com.example.REST_API.dtos.SpellbookRequest;
import com.example.REST_API.dtos.SpellbookResponse;
import com.example.REST_API.entities.Spellbook;
import com.example.REST_API.repositories.UserRepository;
import com.example.REST_API.repositories.SpellbookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class SpellbookServiceImpl implements SpellbookService {
    private final SpellbookRepository spellbookRepository;
    private UserRepository userRepository;
    private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public SpellbookServiceImpl(SpellbookRepository spellbookRepository) {
        this.spellbookRepository = spellbookRepository;
    }

    @Override
    public List<SpellbookResponse> getAllSpellbooks()
    {
        /*return StreamSupport.stream(spellbookRepository.findAll().spliterator(), false)
                .map(entity -> new SpellbookResponse(entity.getSpellbook_id(), entity.getName(), entity.getAge(), entity.getMovie_id().getMovie_id()))
                .collect(Collectors.toList());*/
        List<Spellbook> spellbooks = spellbookRepository.findAll();
        List<SpellbookResponse> spellbookResponses = new ArrayList<>(spellbooks.size());

        for (Spellbook w : spellbooks) {
            SpellbookResponse spellbookResponse = new SpellbookResponse(w.getSpellbookId(), w.getText(), w.getTrainer(), w.getRank(), w.getUserId()); //, a.getMovie());
            spellbookResponses.add(spellbookResponse);
        }

        LOG.info("getAll - number of spellbooks: " + spellbookResponses.size());
        return spellbookResponses;
    }

    @Override
    public void addSpellbook(SpellbookRequest spellbookRequest)
    {
        Spellbook spellbook = new Spellbook();
        spellbook.setSpellbookId(spellbookRequest.getSpellbookId());
        spellbook.setText(spellbookRequest.getText());
        spellbook.setTrainer(spellbookRequest.getTrainer());
        spellbook.setRank(spellbookRequest.getRank());
        spellbook.setUserId(spellbookRequest.getUser());

        spellbookRepository.save(spellbook);

    }

    @Override
    public Spellbook getSpellbookById(Long id) {
        return spellbookRepository.findById(id).get();
    }

    @Override
    public void updateSpellbook(Long id, Spellbook spellbookArg) {
        Spellbook spellbook = spellbookRepository.findById(id).get();
        System.out.println(spellbook.toString());
        spellbook.setSpellbookId(spellbookArg.getSpellbookId());
        spellbook.setText(spellbookArg.getText());
        spellbook.setTrainer(spellbookArg.getTrainer());
        spellbook.setSpellbookId(spellbookArg.getSpellbookId());
        spellbook.setUserId(spellbookArg.getUserId());
        spellbookRepository.save(spellbook);
    }

    @Override
    public void deleteSpellbook(Long id) {
        spellbookRepository.deleteById(id);
    }
}
