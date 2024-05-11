package com.miacademia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "users")
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private boolean status;

    @Field
    private List<Rol> roles;
}
