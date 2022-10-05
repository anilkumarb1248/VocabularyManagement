package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.enums.LearningStatus;
import com.vocabulary.learning.app.model.Verb;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitialService {

    private final VerbService verbService;

    public InitialService(VerbService verbService){
        this.verbService = verbService;
    }

    public void setupInitial() {
        List<Verb> verbList = new ArrayList<>();

        try {
            File file = new File("C:\\Users\\anilb\\OneDrive\\Desktop\\verbs.txt");
            if(file.exists()){
                try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)){
                    String line;
                    System.out.println("****************** Start **********************");
                    while ((line = br.readLine()) != null) {
                        String[] verbs = line.split(",");
                        if(verbs != null && verbs.length > 1){
                            Verb verb = new Verb();
                            if(verbs.length == 1){
                                verb.setBaseForm(verbs[0].trim());
                                verb.setPastTenseForm(verbs[0].trim());
                                verb.setPastParticipleForm(verbs[0].trim());
                            } else if(verbs.length == 2){
                                verb.setBaseForm(verbs[0].trim());
                                verb.setPastTenseForm(verbs[1].trim());
                                verb.setPastParticipleForm(verbs[1].trim());
                            }else if(verbs.length == 3){
                                verb.setBaseForm(verbs[0].trim());
                                verb.setPastTenseForm(verbs[1].trim());
                                verb.setPastParticipleForm(verbs[2].trim());
                            }else if(verbs.length == 4){
                                verb.setBaseForm(verbs[0].trim());
                                verb.setPastTenseForm(verbs[1].trim());
                                verb.setPastParticipleForm(verbs[2].trim());
                                verb.setThirdPersonBaseForm(verbs[3].trim());
                            }else if(verbs.length == 5){
                                verb.setBaseForm(verbs[0].trim());
                                verb.setPastTenseForm(verbs[1].trim());
                                verb.setPastParticipleForm(verbs[2].trim());
                                verb.setThirdPersonBaseForm(verbs[3].trim());
                                verb.setProgressiveForm(verbs[4].trim());
                            }

                            if(StringUtils.isBlank(verb.getThirdPersonBaseForm())){
                                verb.setThirdPersonBaseForm(verb.getBaseForm()+"s");
                            }
                            if(StringUtils.isBlank(verb.getProgressiveForm())){
                                verb.setProgressiveForm(verb.getBaseForm()+"ing");
                            }
                            System.out.println(verb.toStringFormat());
                            verb.setLearningStatus(LearningStatus.NOT_STARTED);
                            verbList.add(verb);
                        }
                    }
                    System.out.println("****************** End **********************");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        verbService.insertVerbs(verbList);
    }

}
