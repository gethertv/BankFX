package pl.edu.pwste.bank.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import pl.edu.pwste.bank.Bank;
import pl.edu.pwste.bank.data.Transfer;
import pl.edu.pwste.bank.data.UserManager;
import pl.edu.pwste.bank.utils.LoadFXML;

import java.net.URL;
import java.util.ResourceBundle;



public class PanelController implements Initializable {

    @FXML
    public Label page;

    @FXML
    private Label balance;

    @FXML
    private Label balanceText;

    @FXML
    private Label nrAccount;

    @FXML
    private AnchorPane transferBox;

    @FXML
    private void nextPage(ActionEvent event) {
        if(Bank.page==Bank.lastPage)
            return;

        Bank.page += 1;
        LoadFXML.loadPanel();
    }

    @FXML
    private void previousPage(ActionEvent event) {
        if(Bank.page==1)
            return;
        Bank.page -= 1;
        LoadFXML.loadPanel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nrAccount.setText(""+UserManager.getUser().getNumberAccount());
        balance.setText(""+TransferController.round(UserManager.getUser().getBalance(),2));

        transferBox.getChildren().clear();
        UserManager.getUser().getTransferList().clear();

        Bank.getSql().loadTransferUser();
        Bank.lastPage = (int) Math.ceil(UserManager.getUser().getTransferList().size()/(double)Bank.pageLimit);

        int x = 20;
        int y = 20;
        //int i = 0;
        //for(Transfer transfer : UserManager.getUser().getTransferList())
        int startIndex = Bank.page*Bank.pageLimit-Bank.pageLimit;
        int finishIndex = Bank.page*Bank.pageLimit;
        for(int i = startIndex; i < finishIndex;i++)
        {
            if(UserManager.getUser().getTransferList().size() <= i || UserManager.getUser().getTransferList().get(i)==null)
                break;

            Transfer transfer = UserManager.getUser().getTransferList().get(i);

            //System.out.println("#"+transfer.getAccountFrom());
            //System.out.println("#"+transfer.getAccountTo());
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setMinSize(570, 60);
            anchorPane.setMaxSize(570, 60);
            anchorPane.setLayoutX(x);
            anchorPane.setLayoutY(y);
            anchorPane.setStyle("-fx-background-color: #f1f1f1");

            // numberFrom
            Label numberFrom = new Label();
            numberFrom.setText(transfer.getName() + " "+transfer.getSurname());
            numberFrom.setLayoutY(4);
            numberFrom.setLayoutX(20);
            numberFrom.setStyle("-fx-font-size: 22px;");
            anchorPane.getChildren().add(numberFrom);

            // numberTo
            Label numberTo = new Label();
            numberTo.setText(transfer.getTitle());
            numberTo.setLayoutY(30);
            numberTo.setLayoutX(20);
            numberTo.setStyle("-fx-font-size: 18px");
            anchorPane.getChildren().add(numberTo);

            // data
            Label data = new Label();
            data.setText(transfer.getData());
            data.setLayoutY(4);
            data.setLayoutX(420);
            data.setMinWidth(100);
            data.setStyle("-fx-alignment: CENTER-RIGHT;-fx-font-size: 14px");
            anchorPane.getChildren().add(data);

            // numberTo
            Label price = new Label();
            price.setText(
                    ((transfer.getAccountTo().equalsIgnoreCase(UserManager.getUser().getNumberAccount())
                     ? "+"+transfer.getMoney() : "-"+transfer.getMoney()))
                    );
            price.setLayoutY(16);
            price.setLayoutX(420);
            price.setMinWidth(100);
            price.setStyle("-fx-alignment: CENTER-RIGHT;-fx-font-size: 28px;-fx-font-weight: 600;"+((transfer.getAccountTo().equalsIgnoreCase(UserManager.getUser().getNumberAccount()) ? "-fx-text-fill: #5bb95b;" : "-fx-text-fill: #fc6464;")));
            anchorPane.getChildren().add(price);

            transferBox.getChildren().add(anchorPane);

            //i++;
            y+=70;

        }

        page.setText("Strona "+Bank.page+"/"+Bank.lastPage);

    }

    public void copyNumberAccount(ActionEvent mouseEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(UserManager.getUser().getNumberAccount());
        clipboard.setContent(content);
    }
}
