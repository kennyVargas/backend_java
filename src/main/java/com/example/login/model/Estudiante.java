package com.example.login.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estudiante")
@Data
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "cod_estudiante", nullable = false, length = 10,unique = true)
    private String codEstudiante;

    @Column(name = "horas_acomuladas",nullable = false)
    private Integer horasAcomuladas;

    @Column(nullable = false)
    private String programaAcademico;

    @Column(nullable = false)
    private Integer semestre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEstudiante estado = EstadoEstudiante.ACTIVO;

    public String getNombreCompleto(){
        return "";
    }
}
