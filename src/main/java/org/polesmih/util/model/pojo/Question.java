package org.polesmih.util.model.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {

    private String id;
    private String question;
    private String[] options;
    private String answer;
    private String description;

}
