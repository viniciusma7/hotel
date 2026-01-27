package br.ifs.cads.api.hotel.entity;

import br.ifs.cads.api.hotel.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"hospede", "quarto"})
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @Column(name = "data_reserva", nullable = false)
    private LocalDateTime dataReserva;

    @Column(name = "data_check_in", nullable = false)
    private LocalDate dataCheckIn;

    @Column(name = "data_check_out", nullable = false)
    private LocalDate dataCheckOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva", nullable = false)
    private StatusReserva statusReserva = StatusReserva.PENDENTE;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Reserva(Hospede hospede, Quarto quarto, LocalDateTime dataReserva,
                   LocalDate dataCheckIn, LocalDate dataCheckOut, Double valorTotal) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataReserva = dataReserva;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.valorTotal = valorTotal;
        this.statusReserva = StatusReserva.PENDENTE;
        this.ativo = true;
    }

    public Reserva(Hospede hospede, Quarto quarto, LocalDateTime dataReserva,
                   LocalDate dataCheckIn, LocalDate dataCheckOut, Double valorTotal,
                   String observacoes) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataReserva = dataReserva;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
        this.statusReserva = StatusReserva.PENDENTE;
        this.ativo = true;
    }
}
