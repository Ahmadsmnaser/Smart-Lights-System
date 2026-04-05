package com.smartlights.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Light {

    @Id
    private String id;      // L1, L2, L3...

    private String name;    // Light 1, Light 2...

    private boolean active; // current ON/OFF state

    private boolean paused; // whether Quartz job is paused
}
