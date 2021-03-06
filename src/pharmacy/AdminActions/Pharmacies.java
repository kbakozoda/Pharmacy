package pharmacy.AdminActions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import pharmacy.Models.HistoryElement;
import pharmacy.Models.Pharmacy;
import pharmacy.Models.StockElement;
import pharmacy.Models.User;
import pharmacy.Services.HistoryService;
import pharmacy.Services.PharmacyService;
import pharmacy.Services.StockService;
import pharmacy.Services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class Pharmacies extends ActionSupport implements ModelDriven<Pharmacy> {

    private String username;
    private int networkId;
    private String phUsername;
    private int phId;
    private Pharmacy pharmacy;
    private List<Pharmacy> list;
    List<HistoryElement> hslist;
    List<StockElement> stlist;
    private PharmacyService service = new PharmacyService();

    @SkipValidation
    public String execute() {
        username = getUsername();
        networkId = getNetworkId();
        // TODO: Error if networkid is not detected
        list = service.getAllForNetwork(networkId);

        return Action.SUCCESS;
    }
    @SkipValidation
    public String create() {
        pharmacy = new Pharmacy();
        return Action.SUCCESS;
    }

    public String doCreate() {
        pharmacy.setNetworkId(getNetworkId());
        pharmacy.setPharmacistId(-1);
        service.insert(pharmacy);
        return Action.SUCCESS;
    }

    @SkipValidation
    public String history() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        phId = Integer.parseInt(request.getParameter("id"));

        if (phId == 0) {
            return Action.ERROR;
        }

        HistoryService hservice = new HistoryService();
        hslist = hservice.getAllByPh(phId);

        return Action.SUCCESS;
    }
    @SkipValidation
    public String historyDel() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        int phId = Integer.parseInt(request.getParameter("id"));
        if(phId == 0) return Action.ERROR;
        HistoryService hserv = new HistoryService();
        hserv.delAllByPhId(phId);
        return Action.SUCCESS;
    }

    @SkipValidation
    public String delete() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        int id = Integer.parseInt(request.getParameter("id"));
        PharmacyService service = new PharmacyService();
        int res = service.deleteById(id);

        if (res == 1) return Action.SUCCESS;

        else return Action.ERROR;
    }

    @SkipValidation
    public String stock() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        int id = Integer.parseInt(request.getParameter("id"));

        StockService sservice = new StockService();

        stlist = sservice.getByPhId(id);

        return Action.SUCCESS;
    }

    @SkipValidation
    public String edit() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        int id = Integer.parseInt(request.getParameter("id"));

        if (id == 0) return Action.ERROR;

        pharmacy = service.getById(id);
        return Action.SUCCESS;
    }

    @SkipValidation
    public String info() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        int id = Integer.parseInt(request.getParameter("id"));
        if (id == 0) return Action.ERROR;
        pharmacy = service.getById(id);
        if (pharmacy.getPharmacistId() == -1) phUsername = "No Pharmacist";
        else
            phUsername = new UserService().getById(pharmacy.getPharmacistId()).getUsername();
        return Action.SUCCESS;
    }

    public String update() {
        service.update(pharmacy);
        return Action.SUCCESS;
    }

    public void validate() {
        List<Pharmacy> list = service.getAll();
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).getId() != pharmacy.getId() &&
                    list.get(i).getNumber() == pharmacy.getNumber()) {
                addActionError("A Pharmacy with such id already exists!");
            }
        }

        if (pharmacy.getAddress().length() == 0 || pharmacy.getAddress().length() > 30) {
            addActionError("Address length is inappropriate");
        }
    }

    public Pharmacy getModel() {
        return pharmacy;
    }

    public String getUsername() {
        Map session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");

        return user.getUsername();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public int getNetworkId() {
        Map session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");

        return user.getNetworkdId();
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public List<HistoryElement> getHslist() {
        return hslist;
    }

    public void setHslist(List<HistoryElement> hslist) {
        this.hslist = hslist;
    }

    public List<StockElement> getStlist() {
        return stlist;
    }

    public void setStlist(List<StockElement> stlist) {
        this.stlist = stlist;
    }

    public String getPhUsername() {
        return phUsername;
    }

    public void setPhUsername(String phUsername) {
        this.phUsername = phUsername;
    }

    public int getPhId() {
        return phId;
    }

    public void setPhId(int phId) {
        this.phId = phId;
    }
}
