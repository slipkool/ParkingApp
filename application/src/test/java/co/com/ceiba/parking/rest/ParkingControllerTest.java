package co.com.ceiba.parking.rest;

import co.com.ceiba.parking.RestApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ParkingControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void verifySaveVehicle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE124\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE124"))
                .andDo(print());
    }

    @Test
    public void verifySaveVehicleExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Vehicle already exists"))
                .andDo(print());
    }

    @Test
    public void verifySaveVehicleMalformed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" : \"1\",\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"DJR847\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Vehicle malformed, id must not be defined"))
                .andDo(print());
    }

    @Test
    public void verifyVehicleByLicenceNumber() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/QWE125").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());
    }

    @Test
    public void verifyVehicleByLicenceNumberNoExist() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/XXX000").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Vehicle not found:XXX000"))
                .andDo(print());
    }

    @Test
    public void verifyVehicleAllList() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(greaterThan(0)))).andDo(print());
    }

    @Test
    public void verifyUpdateVehicle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());

        LocalDateTime ldt = LocalDateTime.now();

        mockMvc.perform(MockMvcRequestBuilders.patch("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" : \"2\",\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"inDate\" : \"" + ldt.withNano(0) + "\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andExpect(jsonPath("$.inDate").value(ldt.withNano(0).toString()))
                .andDo(print());
    }

    @Test
    public void verifyUpdateVehicleInvalid() throws Exception{
        LocalDateTime ldt = LocalDateTime.now();
        mockMvc.perform(MockMvcRequestBuilders.patch("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" : \"3\",\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"XXX001\", \"inDate\" : \"" + ldt.withNano(0) + "\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Vehicle to update does not exist"))
                .andDo(print());
    }

    @Test
    public void verifyVehicleDelete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"typeVehicle\" : \"Motorcycle\", \"licenceNumber\" : \"QWE125\", \"cylinderCapacity\" : \"250\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.typeVehicle").exists())
                .andExpect(jsonPath("$.licenceNumber").exists())
                .andExpect(jsonPath("$.inDate").exists())
                .andExpect(jsonPath("$.typeVehicle").value("Motorcycle"))
                .andExpect(jsonPath("$.licenceNumber").value("QWE125"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/QWE125").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Vehicle has been deleted"))
                .andDo(print());
    }

    @Test
    public void verifyVehicleDeleteInvalid() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/XXX001").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Vehicle not found:XXX001"))
                .andDo(print());
    }
}
