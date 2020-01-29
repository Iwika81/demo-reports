package pe.com.prymera.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.AxesGridLines;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pe.com.prymera.assembler.ReporteEficienciaGeneralDtoAssembler;
import pe.com.prymera.assembler.ReporteEficienciaVisitaDtoAssembler;
import pe.com.prymera.dto.EficienciaGeneralAgenciaDto;
import pe.com.prymera.dto.EficienciaGeneralAnalistaDto;
import pe.com.prymera.dto.EficienciaVisitaAgenciaDto;
import pe.com.prymera.dto.EficienciaVisitaAnalistaDto;
import pe.com.prymera.dto.ReporteEficienciaGeneralDto;
import pe.com.prymera.service.DataService;

@Component
//@Scope(value = "session")
@ViewScoped
@Getter
@Setter
@Log
public class DemoWeb implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private DataService service;
	@Inject
	private ReporteEficienciaGeneralDtoAssembler reporteEficienciaDtoAssembler;
	@Inject
	private ReporteEficienciaVisitaDtoAssembler reporteEficienciaVisitaDtoAssembler;

	// Reporte Gerencial
	private List<EficienciaGeneralAgenciaDto> agencias;
	private TreeNode rootGeneral;
	private BarChartModel barCharGeneral;
	private BarChartModel mixedGeneral;
	private BarChartModel barCharHuacho;
	private BarChartModel mixedHuacho;
	private BarChartModel barCharBarranca;
	private BarChartModel mixedBarranca;
	private BarChartModel barCharPlazaNorte;
	private BarChartModel mixedPlazaNorte;

	// Reporte Visitas
	private List<EficienciaVisitaAgenciaDto> visitasXAgencia;
	private TreeNode rootVisita;
	private BarChartModel barCharVisitaAgencias;
	private BarChartModel mixedVisitaAgencias;
	private BarChartModel barCharVisitaHuacho;
	private BarChartModel mixedVisitaHuacho;
	private BarChartModel barCharVisitaBarranca;
	private BarChartModel mixedVisitaBarranca;
	private BarChartModel barCharVisitaPlazaNorte;
	private BarChartModel mixedVisitaPlazaNorte;

	// Reporte Desembolso Gestion
	private BarChartModel mixedModelGeneral = new BarChartModel();
	private BarChartModel mixedModelAgencias = new BarChartModel();
	private BarChartModel mixedModelEfectividad = new BarChartModel();

	@PostConstruct
	public void init() {
		agencias = service.crearResultas();
		visitasXAgencia = service.crearDataReporteVisita();
		generarReporteGeneral();
		generarReporteVisitas();
		mixedModelGeneral = generarReporteDesembolsoGestion();
		mixedModelAgencias = generarReporteDesembolsoGestionAgencias();
		mixedModelEfectividad= generarReporteDesembolsoGestionEfectividad();
	}

	public void generarReporteGeneral() {
		rootGeneral = generarTree();
		barCharGeneral = generarBarCharGeneral(agencias, "Clientes Nuevos por Agencias");
		mixedGeneral = generarMixBarCharGeneral(agencias, "Cartera de clientes por Agencias");
		clearCharsGeneral();
		for (EficienciaGeneralAgenciaDto agencia : agencias) {
			if (agencia.getId().equals("0001")) { // HUACHO
				barCharHuacho = generarBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Clientes Nuevos - ");
				mixedHuacho = generarMixBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Cartera de clientes - ");
			} else if (agencia.getId().equals("0004")) { // Barranca
				barCharBarranca = generarBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Clientes Nuevos - ");
				mixedBarranca = generarMixBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Cartera de clientes - ");
			} else if (agencia.getId().equals("0006")) { // Plaza Norte
				barCharPlazaNorte = generarBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Clientes Nuevos - ");
				mixedPlazaNorte = generarMixBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Cartera de clientes - ");
			}
		}
	}

	public void generarReporteVisitas() {
		rootVisita = generarTreeVisitas();
		barCharVisitaAgencias = generarBarCharVisitas(visitasXAgencia, "Visitas por Agencias");
		clearCharsVisitas();
		for (EficienciaVisitaAgenciaDto agencia : visitasXAgencia) {
			if (agencia.getId().equals("0001")) { // HUACHO
				barCharVisitaHuacho = generarBarCharVisitasXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Visitas por Agencias");
				// mixedVisitaHuacho = generarBarCharVisitasXAgencia(agencia.getAnalistas(),
				// agencia.getNombre());
			} else if (agencia.getId().equals("0004")) { // Barranca
				barCharVisitaBarranca = generarBarCharVisitasXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Visitas por Agencias");
				// mixedVisitaBarranca = generarBarCharVisitasXAgencia(agencia.getAnalistas(),
				// agencia.getNombre());
			} else if (agencia.getId().equals("0006")) { // Plaza Norte
				barCharVisitaPlazaNorte = generarBarCharVisitasXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Visitas por Agencias");
				// mixedVisitaPlazaNorte = generarBarCharVisitasXAgencia(agencia.getAnalistas(),
				// agencia.getNombre());
			}
		}
	}

	public BarChartModel generarReporteDesembolsoGestion() {
		BarChartModel mixedModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetCampania = new BarChartDataSet();
		List<Number> valuesCampania = new ArrayList<>();
		valuesCampania.add(10);
		valuesCampania.add(0);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(20);
		dataSetCampania.setData(valuesCampania);
		dataSetCampania.setLabel("Campanias");
		dataSetCampania.setBackgroundColor("rgb(255, 99, 132)");
		dataSetCampania.setYaxisID("linearYAxesCantidades");

		BarChartDataSet dataSetGestiones = new BarChartDataSet();
		List<Number> valuesGestiones = new ArrayList<>();
		valuesGestiones.add(5);
		valuesGestiones.add(0);
		valuesGestiones.add(20);
		valuesGestiones.add(30);
		valuesGestiones.add(25);
		valuesGestiones.add(28);
		valuesGestiones.add(30);
		valuesGestiones.add(35);
		valuesGestiones.add(28);
		valuesGestiones.add(38);
		valuesGestiones.add(24);
		valuesGestiones.add(38);
		valuesGestiones.add(19);
		dataSetGestiones.setData(valuesGestiones);
		dataSetGestiones.setLabel("Gestiones");
		dataSetGestiones.setYaxisID("linearYAxesCantidades");
		dataSetGestiones.setBackgroundColor("rgb(75, 192, 192)");

		BarChartDataSet dataSetDesembolso = new BarChartDataSet();
		List<Number> valuesDesembolso = new ArrayList<>();
		valuesDesembolso.add(2);
		valuesDesembolso.add(0);
		valuesDesembolso.add(10);
		valuesDesembolso.add(20);
		valuesDesembolso.add(15);
		valuesDesembolso.add(5);
		valuesDesembolso.add(20);
		valuesDesembolso.add(26);
		valuesDesembolso.add(25);
		valuesDesembolso.add(23);
		valuesDesembolso.add(19);
		valuesDesembolso.add(10);
		valuesDesembolso.add(10);
		dataSetDesembolso.setData(valuesDesembolso);
		dataSetDesembolso.setLabel("Desembolsos");
		dataSetDesembolso.setYaxisID("linearYAxesCantidades");
		dataSetDesembolso.setBackgroundColor("rgb(75, 140, 150)");

		LineChartDataSet dataSetPorcentajes = new LineChartDataSet();
		List<Number> valuesPorcentajes = new ArrayList<>();
		valuesPorcentajes.add(0.5);
		valuesPorcentajes.add(0);
		valuesPorcentajes.add(0.8);
		valuesPorcentajes.add(0.6);
		valuesPorcentajes.add(0.2);
		valuesPorcentajes.add(0.40);
		valuesPorcentajes.add(0.23);
		valuesPorcentajes.add(0.24);
		valuesPorcentajes.add(0.27);
		valuesPorcentajes.add(0.35);
		valuesPorcentajes.add(0.15);
		valuesPorcentajes.add(0);
		valuesPorcentajes.add(0.5);
		dataSetPorcentajes.setData(valuesPorcentajes);
		dataSetPorcentajes.setLabel("Efectividad de Gestion");
		dataSetPorcentajes.setYaxisID("linearYAxesPorcentaje");
		dataSetPorcentajes.setFill(false);
		dataSetPorcentajes.setBackgroundColor("rgb(0, 0, 255)");
		dataSetPorcentajes.setPointBackgroundColor("rgb(0, 0, 255)");
		dataSetPorcentajes.setShowLine(true);

		LineChartDataSet dataSetPorcentajesDesembolso = new LineChartDataSet();
		List<Number> valuesPorcentajesDesembolso = new ArrayList<>();
		valuesPorcentajesDesembolso.add(0.2);
		valuesPorcentajesDesembolso.add(0);
		valuesPorcentajesDesembolso.add(0.3);
		valuesPorcentajesDesembolso.add(0.4);
		valuesPorcentajesDesembolso.add(0.25);
		valuesPorcentajesDesembolso.add(0.27);
		valuesPorcentajesDesembolso.add(0.17);
		valuesPorcentajesDesembolso.add(0.14);
		valuesPorcentajesDesembolso.add(0.16);
		valuesPorcentajesDesembolso.add(0.18);
		valuesPorcentajesDesembolso.add(0.06);
		valuesPorcentajesDesembolso.add(0);
		valuesPorcentajesDesembolso.add(0.22);
		dataSetPorcentajesDesembolso.setData(valuesPorcentajesDesembolso);
		dataSetPorcentajesDesembolso.setLabel("Efectividad de Desembolsos");
		dataSetPorcentajesDesembolso.setYaxisID("linearYAxesPorcentaje");
		dataSetPorcentajesDesembolso.setFill(false);
		dataSetPorcentajesDesembolso.setBackgroundColor("rgb(0, 250, 0)");
		dataSetPorcentajesDesembolso.setPointBackgroundColor("rgb(0, 255, 0)");
		dataSetPorcentajesDesembolso.setShowLine(true);

		data.addChartDataSet(dataSetPorcentajes);
		data.addChartDataSet(dataSetPorcentajesDesembolso);
		data.addChartDataSet(dataSetCampania);
		data.addChartDataSet(dataSetGestiones);
		data.addChartDataSet(dataSetDesembolso);

		List<String> labels = new ArrayList<>();
		labels.add("Huacho");
		labels.add("Miraflores");
		labels.add("Huaral");
		labels.add("Barranca");
		labels.add("Puente Piedra");
		labels.add("Plaza Norte");
		labels.add("San Juan de Lurigancho");
		labels.add("Villa El Salvador");
		labels.add("Gran Chimu");
		labels.add("San Juan de Miraflores");
		labels.add("Comas");
		labels.add("Ceres");
		labels.add("Villa Maria");

		data.setLabels(labels);

		mixedModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();

		CartesianLinearAxes linearYAxesCantidades = new CartesianLinearAxes();
		linearYAxesCantidades.setId("linearYAxesCantidades");
		linearYAxesCantidades.setPosition("left");
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxesCantidades.setTicks(ticks);

		CartesianLinearAxes linearYAxesPorcentaje = new CartesianLinearAxes();
		linearYAxesPorcentaje.setId("linearYAxesPorcentaje");
		linearYAxesPorcentaje.setPosition("right");
		AxesGridLines gridLines = new AxesGridLines();
		gridLines.setDrawBorder(false);
		gridLines.setDrawOnChartArea(false);
		linearYAxesPorcentaje.setGridLines(gridLines);
		CartesianLinearTicks ticksPorcentaje = new CartesianLinearTicks();
		ticksPorcentaje.setBeginAtZero(true);
//		ticksPorcentaje.setMax(1);
//		ticksPorcentaje.setMin(0);		
		linearYAxesPorcentaje.setTicks(ticksPorcentaje);
		linearYAxesPorcentaje.setOffset(true);
		cScales.addYAxesData(linearYAxesCantidades);
		cScales.addYAxesData(linearYAxesPorcentaje);

		options.setScales(cScales);
		mixedModel.setOptions(options);

		return mixedModel;
	}

	public BarChartModel generarReporteDesembolsoGestionAgencias() {
		BarChartModel mixedModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetCampania = new BarChartDataSet();
		List<Number> valuesCampania = new ArrayList<>();
		valuesCampania.add(10);
		valuesCampania.add(0);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(30);
		valuesCampania.add(40);
		valuesCampania.add(20);
		dataSetCampania.setData(valuesCampania);
		dataSetCampania.setLabel("Campanias");
		dataSetCampania.setBackgroundColor("rgb(255, 99, 132)");
		dataSetCampania.setYaxisID("linearYAxesCantidades");

		BarChartDataSet dataSetGestiones = new BarChartDataSet();
		List<Number> valuesGestiones = new ArrayList<>();
		valuesGestiones.add(5);
		valuesGestiones.add(0);
		valuesGestiones.add(20);
		valuesGestiones.add(30);
		valuesGestiones.add(25);
		valuesGestiones.add(28);
		valuesGestiones.add(30);
		valuesGestiones.add(35);
		valuesGestiones.add(28);
		valuesGestiones.add(38);
		valuesGestiones.add(24);
		valuesGestiones.add(38);
		valuesGestiones.add(19);
		dataSetGestiones.setData(valuesGestiones);
		dataSetGestiones.setLabel("Gestiones");
		dataSetGestiones.setYaxisID("linearYAxesCantidades");
		dataSetGestiones.setBackgroundColor("rgb(75, 192, 192)");

		BarChartDataSet dataSetDesembolso = new BarChartDataSet();
		List<Number> valuesDesembolso = new ArrayList<>();
		valuesDesembolso.add(2);
		valuesDesembolso.add(0);
		valuesDesembolso.add(10);
		valuesDesembolso.add(20);
		valuesDesembolso.add(15);
		valuesDesembolso.add(5);
		valuesDesembolso.add(20);
		valuesDesembolso.add(26);
		valuesDesembolso.add(25);
		valuesDesembolso.add(23);
		valuesDesembolso.add(19);
		valuesDesembolso.add(10);
		valuesDesembolso.add(10);
		dataSetDesembolso.setData(valuesDesembolso);
		dataSetDesembolso.setLabel("Desembolsos");
		dataSetDesembolso.setYaxisID("linearYAxesCantidades");
		dataSetDesembolso.setBackgroundColor("rgb(75, 140, 150)");

		data.addChartDataSet(dataSetCampania);
		data.addChartDataSet(dataSetGestiones);
		data.addChartDataSet(dataSetDesembolso);

		List<String> labels = new ArrayList<>();
		labels.add("Huacho");
		labels.add("Miraflores");
		labels.add("Huaral");
		labels.add("Barranca");
		labels.add("Puente Piedra");
		labels.add("Plaza Norte");
		labels.add("San Juan de Lurigancho");
		labels.add("Villa El Salvador");
		labels.add("Gran Chimu");
		labels.add("San Juan de Miraflores");
		labels.add("Comas");
		labels.add("Ceres");
		labels.add("Villa Maria");

		data.setLabels(labels);

		mixedModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();

		CartesianLinearAxes linearYAxesCantidades = new CartesianLinearAxes();
		linearYAxesCantidades.setId("linearYAxesCantidades");
		linearYAxesCantidades.setPosition("left");
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxesCantidades.setTicks(ticks);
		cScales.addYAxesData(linearYAxesCantidades);
		
		options.setScales(cScales);
		mixedModel.setOptions(options);

		return mixedModel;
	}
	
	public BarChartModel generarReporteDesembolsoGestionEfectividad() {
		BarChartModel mixedModel = new BarChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSetPorcentajes = new LineChartDataSet();
		List<Number> valuesPorcentajes = new ArrayList<>();
		valuesPorcentajes.add(0.5);
		valuesPorcentajes.add(0);
		valuesPorcentajes.add(0.8);
		valuesPorcentajes.add(0.6);
		valuesPorcentajes.add(0.2);
		valuesPorcentajes.add(0.40);
		valuesPorcentajes.add(0.23);
		valuesPorcentajes.add(0.24);
		valuesPorcentajes.add(0.27);
		valuesPorcentajes.add(0.35);
		valuesPorcentajes.add(0.15);
		valuesPorcentajes.add(0);
		valuesPorcentajes.add(0.5);
		dataSetPorcentajes.setData(valuesPorcentajes);
		dataSetPorcentajes.setLabel("Efectividad de Gestion");
		dataSetPorcentajes.setYaxisID("linearYAxesPorcentaje");
		dataSetPorcentajes.setFill(false);
		dataSetPorcentajes.setBackgroundColor("rgb(0, 0, 255)");
		dataSetPorcentajes.setPointBackgroundColor("rgb(0, 0, 255)");
		dataSetPorcentajes.setShowLine(true);

		LineChartDataSet dataSetPorcentajesDesembolso = new LineChartDataSet();
		List<Number> valuesPorcentajesDesembolso = new ArrayList<>();
		valuesPorcentajesDesembolso.add(0.2);
		valuesPorcentajesDesembolso.add(0);
		valuesPorcentajesDesembolso.add(0.3);
		valuesPorcentajesDesembolso.add(0.4);
		valuesPorcentajesDesembolso.add(0.25);
		valuesPorcentajesDesembolso.add(0.27);
		valuesPorcentajesDesembolso.add(0.17);
		valuesPorcentajesDesembolso.add(0.14);
		valuesPorcentajesDesembolso.add(0.16);
		valuesPorcentajesDesembolso.add(0.18);
		valuesPorcentajesDesembolso.add(0.06);
		valuesPorcentajesDesembolso.add(0);
		valuesPorcentajesDesembolso.add(0.22);
		dataSetPorcentajesDesembolso.setData(valuesPorcentajesDesembolso);
		dataSetPorcentajesDesembolso.setLabel("Efectividad de Desembolsos");
		dataSetPorcentajesDesembolso.setYaxisID("linearYAxesPorcentaje");
		dataSetPorcentajesDesembolso.setFill(false);
		dataSetPorcentajesDesembolso.setBackgroundColor("rgb(0, 250, 0)");
		dataSetPorcentajesDesembolso.setPointBackgroundColor("rgb(0, 255, 0)");
		dataSetPorcentajesDesembolso.setShowLine(true);

		data.addChartDataSet(dataSetPorcentajes);
		data.addChartDataSet(dataSetPorcentajesDesembolso);

		List<String> labels = new ArrayList<>();
		labels.add("Huacho");
		labels.add("Miraflores");
		labels.add("Huaral");
		labels.add("Barranca");
		labels.add("Puente Piedra");
		labels.add("Plaza Norte");
		labels.add("San Juan de Lurigancho");
		labels.add("Villa El Salvador");
		labels.add("Gran Chimu");
		labels.add("San Juan de Miraflores");
		labels.add("Comas");
		labels.add("Ceres");
		labels.add("Villa Maria");

		data.setLabels(labels);

		mixedModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();

//		CartesianLinearAxes linearYAxesCantidades = new CartesianLinearAxes();
//		linearYAxesCantidades.setId("linearYAxesCantidades");
//		linearYAxesCantidades.setPosition("left");
//		CartesianLinearTicks ticks = new CartesianLinearTicks();
//		ticks.setBeginAtZero(true);
//		linearYAxesCantidades.setTicks(ticks);

		CartesianLinearAxes linearYAxesPorcentaje = new CartesianLinearAxes();
		linearYAxesPorcentaje.setId("linearYAxesPorcentaje");
		linearYAxesPorcentaje.setPosition("right");
//		AxesGridLines gridLines = new AxesGridLines();
//		gridLines.setDrawBorder(false);
//		gridLines.setDrawOnChartArea(false);
//		linearYAxesPorcentaje.setGridLines(gridLines);
		CartesianLinearTicks ticksPorcentaje = new CartesianLinearTicks();
		ticksPorcentaje.setBeginAtZero(true);
//		ticksPorcentaje.setMax(1);
//		ticksPorcentaje.setMin(0);		
		linearYAxesPorcentaje.setTicks(ticksPorcentaje);
		linearYAxesPorcentaje.setOffset(true);
		//cScales.addYAxesData(linearYAxesCantidades);
		cScales.addYAxesData(linearYAxesPorcentaje);

		options.setScales(cScales);
		mixedModel.setOptions(options);

		return mixedModel;
	}

	public void filtar() {
		log.info("filtrar");
		EficienciaGeneralAgenciaDto plazaNorte = new EficienciaGeneralAgenciaDto("0006", "Plaza Norte",
				new ArrayList<>());
		EficienciaGeneralAnalistaDto p1 = new EficienciaGeneralAnalistaDto("Sofia Caballero", "Junior", 3,
				new BigDecimal(1500.0), 3, new BigDecimal(1500.0), 2, new BigDecimal(2000.0), 8, 3,
				new BigDecimal(4500.0), 6, 2, new BigDecimal(5500.0));
		plazaNorte.getAnalistas().add(p1);
		agencias.add(plazaNorte);
		generarReporteGeneral();
	}

	/// Metodos para generar Graficos General
	public DefaultTreeNode generarTree() {
		DefaultTreeNode root = new DefaultTreeNode(new ReporteEficienciaGeneralDto(), null);
		for (EficienciaGeneralAgenciaDto agencia : agencias) {
			TreeNode var = new DefaultTreeNode(reporteEficienciaDtoAssembler.toDto(agencia), root);
			for (EficienciaGeneralAnalistaDto analista : agencia.getAnalistas()) {
				new DefaultTreeNode(reporteEficienciaDtoAssembler.toDto(analista), var);
			}
		}
		return root;
	}

	public void clearCharsGeneral() {
		barCharHuacho = null;
		mixedHuacho = null;
		barCharBarranca = null;
		mixedBarranca = null;
		barCharPlazaNorte = null;
		mixedPlazaNorte = null;
	}

	public BarChartModel generarBarCharGeneral(List<EficienciaGeneralAgenciaDto> agencias, String titulo) {
		BarChartModel barCharAgencias = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Clientes  nuevos");
		dataSetClientesNuevos.setBackgroundColor("rgb(255, 99, 132)");
		dataSetClientesNuevos.setData(agencias.stream().map(m -> m.getNuevoDesembolzos()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Campanias");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias.setData(agencias.stream().map(m -> m.getCampaniaDesembolzos()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		BarChartDataSet dataSetOtros = new BarChartDataSet();
		dataSetOtros.setLabel("Otros");
		dataSetOtros.setBackgroundColor("rgb(75, 140, 150)");
		dataSetOtros.setData(agencias.stream().map(m -> m.getOtroDesembolzos()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetOtros);

		// LABELS
		data.setLabels(agencias.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		barCharAgencias.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo);
		options.setTitle(title);

		barCharAgencias.setOptions(options);
		// End of Options
		return barCharAgencias;
	}

	public BarChartModel generarBarCharXAgencia(List<EficienciaGeneralAnalistaDto> list, String nombreAgencia,
			String titulo) {
		BarChartModel model = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Clientes  nuevos");
		dataSetClientesNuevos.setBackgroundColor("rgb(255, 99, 132)");
		dataSetClientesNuevos.setData(list.stream().map(m -> m.getNuevoDesembolzo()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Campanias");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias.setData(list.stream().map(m -> m.getCampaniaDesembolzo()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		BarChartDataSet dataSetOtros = new BarChartDataSet();
		dataSetOtros.setLabel("Otros");
		dataSetOtros.setBackgroundColor("rgb(75, 140, 150)");
		dataSetOtros.setData(list.stream().map(m -> m.getOtroDesembolzo()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetOtros);

		// LABELS
		data.setLabels(list.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		model.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo.concat(nombreAgencia));
		options.setTitle(title);

		model.setOptions(options);
		// End of Options

		return model;
	}

	public BarChartModel generarMixBarCharGeneral(List<EficienciaGeneralAgenciaDto> agencias, String titulo) {
		BarChartModel mixedAgencias = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetCarteraActual = new BarChartDataSet();
		dataSetCarteraActual.setLabel("Cartera actual");
		// dataSetCarteraActual.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraActual.setBackgroundColor("rgba(177, 99, 255)");
		dataSetCarteraActual.setData(agencias.stream().map(m -> m.getCarteraClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraActual);

		BarChartDataSet dataSetCarteraAnterior = new BarChartDataSet();
		dataSetCarteraAnterior.setLabel("Cartera Anterior");
		dataSetCarteraAnterior.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraAnterior.setBackgroundColor("rgba(255, 99, 132)");
		dataSetCarteraAnterior
				.setData(agencias.stream().map(m -> m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraAnterior);

		LineChartDataSet dataSetDiferencia = new LineChartDataSet();
		dataSetDiferencia.setData(agencias.stream().map(m -> m.getCarteraClientes() - m.getCarteraAnteriorClientes())
				.collect(Collectors.toList()));
		// dataSetDiferencia.getData().addAll(agencias.stream().map(m ->
		// m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		dataSetDiferencia.setLabel("Diferencia");
		dataSetDiferencia.setFill(false);
		dataSetDiferencia.setBorderColor("rgb(54, 162, 235)");
		// data.addChartDataSet(dataSetDiferencia);

		// LABELS
		data.setLabels(agencias.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		mixedAgencias.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearXAxes = new CartesianLinearAxes();

		CartesianLinearAxes linearYAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxes.setTicks(ticks);
		// linearYAxes.setStacked(true);
		linearYAxes.setOffset(false);

		cScales.addXAxesData(linearXAxes);
		cScales.addYAxesData(linearYAxes);
		options.setScales(cScales);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo);
		options.setTitle(title);

		mixedAgencias.setOptions(options);
		// End of Options
		return mixedAgencias;
	}

	public BarChartModel generarMixBarCharXAgencia(List<EficienciaGeneralAnalistaDto> list, String nombreAgencia,
			String titulo) {
		BarChartModel mixModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetCarteraActual = new BarChartDataSet();
		dataSetCarteraActual.setLabel("Cartera actual");
		// dataSetCarteraActual.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraActual.setBackgroundColor("rgba(99, 99, 255)");
		dataSetCarteraActual.setData(list.stream().map(m -> m.getCarteraClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraActual);

		BarChartDataSet dataSetCarteraAnterior = new BarChartDataSet();
		dataSetCarteraAnterior.setLabel("Cartera Anterior");
		dataSetCarteraAnterior.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraAnterior.setBackgroundColor("rgba(255, 99, 132)");
		dataSetCarteraAnterior
				.setData(list.stream().map(m -> m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraAnterior);

		LineChartDataSet dataSetDiferencia = new LineChartDataSet();
		dataSetDiferencia.setData(list.stream().map(m -> m.getCarteraClientes() - m.getCarteraAnteriorClientes())
				.collect(Collectors.toList()));
		// dataSetDiferencia.getData().addAll(agencias.stream().map(m ->
		// m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		dataSetDiferencia.setLabel("Diferencia");
		dataSetDiferencia.setFill(false);
		dataSetDiferencia.setBorderColor("rgb(54, 162, 235)");
		// data.addChartDataSet(dataSetDiferencia);

		// LABELS
		data.setLabels(list.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		mixModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearXAxes = new CartesianLinearAxes();

		CartesianLinearAxes linearYAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxes.setTicks(ticks);
		// linearYAxes.setStacked(true);
		linearYAxes.setOffset(false);

		cScales.addXAxesData(linearXAxes);
		cScales.addYAxesData(linearYAxes);
		options.setScales(cScales);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo.concat(nombreAgencia));
		options.setTitle(title);

		mixModel.setOptions(options);
		// End of Options
		return mixModel;
	}

	/// Metodos para generar Graficos Visitas
	public DefaultTreeNode generarTreeVisitas() {
		DefaultTreeNode root = new DefaultTreeNode(new ReporteEficienciaGeneralDto(), null);
		for (EficienciaVisitaAgenciaDto agencia : visitasXAgencia) {
			TreeNode var = new DefaultTreeNode(reporteEficienciaVisitaDtoAssembler.toDto(agencia), root);
			for (EficienciaVisitaAnalistaDto analista : agencia.getAnalistas()) {
				new DefaultTreeNode(reporteEficienciaVisitaDtoAssembler.toDto(analista), var);
			}
		}
		return root;
	}

	public BarChartModel generarBarCharVisitas(List<EficienciaVisitaAgenciaDto> agencias, String titulo) {
		BarChartModel barCharAgencias = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Periodo Actual- Visitas");
		dataSetClientesNuevos.setBackgroundColor("rgb(255, 99, 132)");
		dataSetClientesNuevos.setData(
				agencias.stream().map(m -> m.getPeriodoActualVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Periodo Anterior - Visitas");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias.setData(
				agencias.stream().map(m -> m.getPeriodoAnteriorVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		// LABELS
		data.setLabels(agencias.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		barCharAgencias.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		// linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo);
		options.setTitle(title);

		barCharAgencias.setOptions(options);
		// End of Options
		return barCharAgencias;
	}

	public void clearCharsVisitas() {
		barCharVisitaHuacho = null;
		mixedVisitaHuacho = null;
		barCharVisitaBarranca = null;
		mixedVisitaBarranca = null;
		barCharVisitaPlazaNorte = null;
		mixedVisitaPlazaNorte = null;
	}

	public BarChartModel generarBarCharVisitasXAgencia(List<EficienciaVisitaAnalistaDto> list, String nombreAgencia,
			String titulo) {
		BarChartModel model = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Periodo Actual- Visitas");
		dataSetClientesNuevos.setBackgroundColor("rgb(60, 186, 159)");
		dataSetClientesNuevos
				.setData(list.stream().map(m -> m.getPeriodoActualVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Periodo Anterior - Visitas");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias
				.setData(list.stream().map(m -> m.getPeriodoAnteriorVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		// LABELS
		data.setLabels(list.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		model.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearAxes linearYAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxes.setTicks(ticks);

//		linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearYAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo.concat(nombreAgencia));
		options.setTitle(title);

		model.setOptions(options);
		// End of Options

		return model;
	}
}
