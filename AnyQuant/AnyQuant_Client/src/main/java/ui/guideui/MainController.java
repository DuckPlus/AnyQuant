package ui.guideui;

import javax.swing.JPanel;

import org.dom4j.Element;

import ui.config.CompomentType;
import ui.listui.BenchMarkListPanel;
import ui.listui.StockListPanel;
import ui.stockdetail.DetailMainPanel;
import ui.tool.ButtonState;
import ui.tool.MyPanel;
import ui.tool.MySideBarButton;
import ui.tool.MySideBarListener;
import ui.tool.PanelController;

/**
 * 主界面跳转控制器
 * 
 * @author czq
 * @date 2016年3月2日
 */
public class MainController extends PanelController {

	private MySideBarButton StockDetailButton;
	private MySideBarButton StockListButton;
	private MySideBarButton BenchmarkButton;

	private final static String stockDetailPanelStr = "stockDetailPanel";
	private final static String stockListPanelStr = "stockListPanel";
	private final static String benchmarkPanelStr = "benchmarkPanel";
	private final static String guidePanelStr = "guidePanel";

	private DetailMainPanel stockDetailPanel;
	private BenchMarkListPanel benchMarkListPanel;
	private StockListPanel stockListPanel;
	private GuidePanel guidePanel;
	private JPanel leftPanel;

	public MainController(MyPanel initialPanel, Element root) {
		super(initialPanel, root.element("changepanel"));

		initialBL();
		initButtons(root.element(CompomentType.BUTTONS.name()));
		initPanel(root);
		addButtons();
		addPanels();
		addListeners();
		addToMap();
		this.setAllButtonVisable(true);
		panelManager.show(changePanel, guidePanelStr);
		changePanel.setVisible(true);

	}

	@Override
	protected void initPanel(Element e) {
		stockDetailPanel = new DetailMainPanel(e.element(stockDetailPanelStr));
		stockListPanel = new StockListPanel(e.element(stockListPanelStr));
		benchMarkListPanel = new BenchMarkListPanel(
				e.element(benchmarkPanelStr));
		guidePanel = new GuidePanel(e.element(guidePanelStr));
//		leftPanel = new JPanel();
//		leftPanel.setBounds(x, y, width, height);
	}

	@Override
	protected void initButtons(Element e) {
		StockDetailButton = new MySideBarButton(e.element("StockDetail"));
		StockListButton = new MySideBarButton(e.element("StockList"));
		BenchmarkButton = new MySideBarButton(e.element("BenchMark"));

	}

	@Override
	protected void addButtons() {
		mainPanel.add(BenchmarkButton);
		mainPanel.add(StockDetailButton);
		mainPanel.add(StockListButton);
	}

	@Override
	protected void addPanels() {
		changePanel.add(benchMarkListPanel, benchmarkPanelStr);
		changePanel.add(stockDetailPanel, stockDetailPanelStr);
		changePanel.add(stockListPanel, stockListPanelStr);
		changePanel.add(guidePanel, guidePanelStr);
	}

	@Override
	protected void addListeners() {
		BenchmarkButton.addMouseListener(new MySideBarListener(BenchmarkButton,
				this, benchmarkPanelStr));
		StockDetailButton.addMouseListener(new MySideBarListener(
				StockDetailButton, this, stockDetailPanelStr));
		StockListButton.addMouseListener(new MySideBarListener(StockListButton,
				this, stockListPanelStr));

	}

	@Override
	public void setAllButtonUnClicked() {
		BenchmarkButton.setMyIcon(ButtonState.NORMAL);
		StockDetailButton.setMyIcon(ButtonState.NORMAL);
		StockListButton.setMyIcon(ButtonState.NORMAL);

	}

	@Override
	public void setAllButtonVisable(boolean state) {
		BenchmarkButton.setVisible(state);
		StockDetailButton.setVisible(state);
		StockListButton.setVisible(state);

	}

	@Override
	protected void addToMap() {
		buttonMap.put(benchmarkPanelStr, BenchmarkButton);
		buttonMap.put(stockDetailPanelStr, StockDetailButton);
		buttonMap.put(stockListPanelStr, StockListButton);

	}

	@Override
	protected void initialBL() {
		// TODO Auto-generated method stub

	}

}
