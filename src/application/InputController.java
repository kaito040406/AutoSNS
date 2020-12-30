package application;

import java.io.IOException;

import common.inputCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainPG.instaAuto;

public class InputController {
	@FXML private TextField loginId;

	@FXML private TextField passWord;

	@FXML private TextField searchWord;

	@FXML private TextField collectionNo;

	@FXML Button button;

	@FXML
	public void onButtonClick(ActionEvent evt) throws IOException {
        inputCheck check =  new inputCheck();
        if(check.numCheck(collectionNo.getText()) && check.halfCheck(loginId.getText()) && check.halfCheck(passWord.getText())) {
        	instaAuto movement = new instaAuto(
        			loginId.getText(),
        			passWord.getText(),
        			searchWord.getText(),
        			Integer.parseInt(collectionNo.getText()));
        	try {
				if(movement.autoIine()) {
					Parent parent = FXMLLoader.load(getClass().getResource("Success.fxml"));
					Scene scene = new Scene(parent,300,100);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.show();
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				Alert alrt = new Alert(AlertType.INFORMATION);
	    		alrt.setTitle("エラー");
	    		alrt.setContentText("処理に失敗しました");
	    		alrt.showAndWait();
			} finally {
				movement.quitIine();
			}
        }else if(collectionNo.getText().equals("") || loginId.getText().equals("") || passWord.getText().equals("")){
        	Alert alrt = new Alert(AlertType.INFORMATION);
    		alrt.setTitle("エラー");
    		alrt.setContentText("入力してください");
    		alrt.showAndWait();
        }
        else {
        	Alert alrt = new Alert(AlertType.INFORMATION);
    		alrt.setTitle("エラー");
    		alrt.setContentText("入力内容に誤りがあります");
    		alrt.showAndWait();
        }
    }
}
