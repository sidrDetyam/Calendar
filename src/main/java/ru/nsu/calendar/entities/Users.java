package ru.nsu.calendar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq")
    @GenericGenerator(name = "pet_seq", strategy = "increment")
    private Long id;

    @Column(name = "telegram_token")
    private String telegramToken;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;
}
