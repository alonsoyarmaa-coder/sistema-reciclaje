package com.iestp.sistemareciclaje.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String hora;

    @Column
    private String nivel;

    @Column
    private String msg;

    public Log() { }

    public Log(String hora, String nivel, String msg) {
        this.hora = hora;
        this.nivel = nivel;
        this.msg = msg;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
}