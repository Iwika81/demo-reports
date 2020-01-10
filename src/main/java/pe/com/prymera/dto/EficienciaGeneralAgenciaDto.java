package pe.com.prymera.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EficienciaGeneralAgenciaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String nombre;
	private List<EficienciaGeneralAnalistaDto> analistas;

	public int getNuevoDesembolzos() {
		return analistas.stream().mapToInt(m -> m.getNuevoDesembolzo()).sum();
	}

	public BigDecimal getNuevoMontos() {
		return analistas.stream().map(m -> m.getNuevoMonto()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public int getCampaniaDesembolzos() {
		return analistas.stream().mapToInt(m -> m.getCampaniaDesembolzo()).sum();
	}

	public BigDecimal getCampaniaMontos() {
		return analistas.stream().map(m -> m.getCampaniaMonto()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public int getOtroDesembolzos() {
		return analistas.stream().mapToInt(m -> m.getOtroDesembolzo()).sum();
	}

	public BigDecimal getOtroMontos() {
		return analistas.stream().map(m -> m.getOtroMonto()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public int getCarteraClientes() {
		return analistas.stream().mapToInt(m -> m.getCarteraClientes()).sum();
	}

	public int getCarteraCreditos() {
		return analistas.stream().mapToInt(m -> m.getCarteraCreditos()).sum();
	}

	public BigDecimal getCarteraSaldos() {
		return analistas.stream().map(m -> m.getCarteraSaldo()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public int getCarteraAnteriorClientes() {
		return analistas.stream().mapToInt(m -> m.getCarteraAnteriorClientes()).sum();
	}

	public int getCarteraAnteriorCreditos() {
		return analistas.stream().mapToInt(m -> m.getCarteraAnteriorCreditos()).sum();
	}

	public BigDecimal getCarteraAnteriorSaldos() {
		return analistas.stream().map(m -> m.getCarteraAnteriorSaldo()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
