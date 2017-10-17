package org.opencds.cqf.cds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Christopher Schuler on 5/1/2017.
 */
public class CdsHooksHelper {

    public static void DisplayDiscovery(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject opioidGuidance = new JSONObject();
        opioidGuidance.put("hook", "medication-prescribe");
        opioidGuidance.put("name", "Opioid Morphine Milligram Equivalence (MME) Guidance Service");
        opioidGuidance.put("description", "CDS Service that finds the MME of an opioid medication and provides guidance to the prescriber if the MME exceeds the recommended range.");
        opioidGuidance.put("id", "opioid-mme-guidance");

        JSONObject prefetchContent = new JSONObject();
        prefetchContent.put("medication", "MedicationRequest?patient={{Patient.id}}&status=active");

        opioidGuidance.put("prefetch", prefetchContent);

        jsonArray.add(opioidGuidance);

        JSONObject medicationPrescribe = new JSONObject();
        medicationPrescribe.put("hook", "medication-prescribe");
        medicationPrescribe.put("name", "User-defined medication-prescribe service");
        medicationPrescribe.put("description", "Enables user to define a CDS Hooks service using naming conventions");
        medicationPrescribe.put("id", "user-medication-prescribe");

        medicationPrescribe.put("prefetch", prefetchContent);

        jsonArray.add(medicationPrescribe);

        JSONObject patientView = new JSONObject();
        patientView.put("hook", "patient-view");
        patientView.put("name", "User-defined patient-view service");
        patientView.put("description", "Enables user to define a CDS Hooks service using naming conventions");
        patientView.put("id", "user-patient-view");

        prefetchContent = new JSONObject();
        prefetchContent.put("patient", "Patient/{{Patient.id}}");
        patientView.put("prefetch", prefetchContent);

        jsonArray.add(patientView);

        jsonResponse.put("services", jsonArray);

        response.getWriter().println(getPrettyJson(jsonResponse.toJSONString()));
    }

    public static String getPrettyJson(String uglyJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(uglyJson);
        return gson.toJson(element);
    }
}
