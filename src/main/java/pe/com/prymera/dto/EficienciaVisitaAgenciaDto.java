package pe.com.prymera.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EficienciaVisitaAgenciaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String nombre;
	private List<EficienciaVisitaAnalistaDto> analistas;

	public int getPeriodoAnteriorVisitasRegistradas() {
		return analistas.stream().mapToInt(m -> m.getPeriodoAnteriorVisitasRegistradas()).sum();
	};

	public int getPeriodoAnteriorClientesNuevos() {
		return analistas.stream().mapToInt(m -> m.getPeriodoAnteriorClientesNuevos()).sum();
	};

	public int getPeriodoAnteriorDesembolsosClientesNuevos() {
		return analistas.stream().mapToInt(m -> m.getPeriodoAnteriorDesembolsosClientesNuevos()).sum();
	};

	public BigDecimal getPeriodoAnteriorMontosClientesNuevos() {
		return analistas.stream().map(m -> m.getPeriodoAnteriorMontosClientesNuevos()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	};

	public int getPeriodoActualVisitasRegistradas() {
		return analistas.stream().mapToInt(m -> m.getPeriodoActualVisitasRegistradas()).sum();
	};

	public int getPeriodoActualClientesNuevos() {
		return analistas.stream().mapToInt(m -> m.getPeriodoActualClientesNuevos()).sum();
	};

	public int getPeriodoActualDesembolsosClientesNuevos() {
		return analistas.stream().mapToInt(m -> m.getPeriodoActualDesembolsosClientesNuevos()).sum();
	};

	public BigDecimal getPeriodoActualMontosClientesNuevos() {
		return analistas.stream().map(m -> m.getPeriodoActualMontosClientesNuevos()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	};
}
