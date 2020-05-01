package Core;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Models.Demographics;
import Models.MemeQuestionnaire;
import Models.Participant;

public class Application {
    private static final String APPLICATION_NAME = "Meme Responses";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    /**
     * Go to https://developers.google.com/sheets/api/quickstart/java and retrieve your credentials.json file.
     * Copy it into the resources directory
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     *  GRAB THE SPREADSHEET ID FROM THE URL OF THE GOOGLE SHEET YOU WISH TO USE
     */
    private static final String spreadsheetId = "1RLBcejItcqzfSPSW79qHG9Mqzg7VAPV0fGqZs6Wx8mg";

    public static void main(String... args) throws IOException, GeneralSecurityException {
        //Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        //Build Google Sheets service
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //Fetch whole valueRange from 'Form Responses 1' sheet
        String tableRange = "Form Responses 1!A1:DY151";
        ValueRange valueRange = service.spreadsheets().values().get(spreadsheetId, tableRange).execute();

        //Create a table from the value range for better workability
        Table responses = new Table(valueRange);

        //Cleanup invalid responses
        Analytics.cleanupData(responses);

        //Extract all participant data from valueRange
        List<Participant> participants = new ArrayList<>(FormInfo.numParticipants);
        for(int i = 1; i < FormInfo.numParticipants + 1; ++i) {
            participants.add(new Participant(i, responses));
        }

        //Create separate pivot table source tables for meme and demographic responses
        Table pivotTable;
        pivotTable = Analytics.createMemeQuestionnairePivotTable(participants);
        pivotTable.export("./memeQuestionnairePivotTableSource.csv");
        pivotTable = Analytics.createDemographicPivotTable(participants);
        pivotTable.export("./demographicsPivotTableSource.csv");

    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        //Load client secrets.
        InputStream in = Application.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        //Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}