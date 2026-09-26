package com.swaasthyasetu.prototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private val Navy = Color(0xFF123B5D)
private val Blue = Color(0xFF1F6FEB)
private val Teal = Color(0xFF0F9D8A)
private val Red = Color(0xFFD92D20)
private val Green = Color(0xFF16803C)
private val Amber = Color(0xFFB7791F)
private val Purple = Color(0xFF6B4FA1)
private val Bg = Color(0xFFF6F8FB)

private enum class Role { PATIENT, ASHA, DOCTOR, HOSPITAL, AMBULANCE, PHARMACY, ADMIN }
private enum class Risk { LOW, MODERATE, HIGH }

private data class Patient(val name: String, val age: Int, val phone: String, val risk: Risk, val vitals: String)
private class AppState {
    var offline: Boolean = false
    var syncPending: Boolean = false
    var risk: Risk = Risk.HIGH
    var referralCreated: Boolean = false
    var consultationDone: Boolean = false
    var prescriptionCreated: Boolean = false
    var medicineDispensed: Boolean = false
    var sosActive by mutableStateOf(false)
    var ambulanceAssigned by mutableStateOf(false)
    var patientDroppedAtHospital by mutableStateOf(false)
    var bedLocked: Boolean = false
    var followUpSaved: Boolean = false
    var voiceText: String = "Patient reports fever and weakness"
    var auditCount: Int = 12
}

private const val LANDING = "landing"
private const val LOGIN = "login"
private const val DASH = "dash"
private const val PATIENT = "patient"
private const val ASHA = "asha"
private const val DOCTOR = "doctor"
private const val HOSPITAL = "hospital"
private const val AMBULANCE = "ambulance"
private const val PHARMACY = "pharmacy"
private const val ADMIN = "admin"
private const val SOS = "sos"
private const val ASHA_REGISTER = "asha_register"
private const val ASHA_VITALS = "asha_vitals"
private const val ASHA_REFERRAL = "asha_referral"
private const val ASHA_FOLLOW = "asha_follow"
private const val ASHA_VOICE = "asha_voice"
private const val DOCTOR_REFERRAL = "doctor_referral"
private const val DOCTOR_CONSULT = "doctor_consult"
private const val DOCTOR_PRESCRIPTION = "doctor_prescription"
private const val HOSPITAL_CAPACITY = "hospital_capacity"
private const val AMBULANCE_TRIP = "ambulance_trip"
private const val PHARMACY_PRESCRIPTION = "pharmacy_prescription"
private const val HEALTH_VAULT = "health_vault"
private const val APPOINTMENTS = "appointments"
private const val TELECONSULT = "teleconsult"
private const val AUDIT = "audit"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SwaasthyaSetuApp() }
    }
}

@Composable
private fun SwaasthyaSetuApp() {
    val nav = rememberNavController()
    val state = remember { mutableStateOf(AppState()) }
    val role = remember { mutableStateOf(Role.PATIENT) }
    MaterialTheme(colorScheme = lightColorScheme(primary = Blue, secondary = Teal, background = Bg, surface = Color.White, error = Red)) {
        Surface(Modifier.fillMaxSize(), color = Bg) {
            NavHost(navController = nav, startDestination = LANDING) {
                composable(LANDING) { Landing(nav, state.value) }
                composable(LOGIN) { Login(nav, role) }
                composable(DASH) { DashboardRouter(nav, role.value, state.value) }
                composable(PATIENT) { PatientPortal(nav, state.value) }
                composable(ASHA) { AshaPortal(nav, state.value) }
                composable(DOCTOR) { DoctorPortal(nav, state.value) }
                composable(HOSPITAL) { HospitalPortal(nav, state.value) }
                composable(AMBULANCE) { AmbulancePortal(nav, state.value) }
                composable(PHARMACY) { PharmacyPortal(nav, state.value) }
                composable(ADMIN) { AdminPortal(nav, state.value) }
                composable(SOS) { SosScreen(nav, state.value) }
                composable(ASHA_REGISTER) { AshaRegister(nav, state.value) }
                composable(ASHA_VITALS) { AshaVitals(nav, state.value) }
                composable(ASHA_REFERRAL) { ReferralScreen(nav, state.value) }
                composable(ASHA_FOLLOW) { FollowUpScreen(nav, state.value) }
                composable(ASHA_VOICE) { VoiceScreen(nav, state.value) }
                composable(DOCTOR_REFERRAL) { DoctorReferral(nav, state.value) }
                composable(DOCTOR_CONSULT) { DoctorConsult(nav, state.value) }
                composable(DOCTOR_PRESCRIPTION) { PrescriptionScreen(nav, state.value) }
                composable(HOSPITAL_CAPACITY) { CapacityScreen(nav, state.value) }
                composable(AMBULANCE_TRIP) { AmbulanceTrip(nav, state.value) }
                composable(PHARMACY_PRESCRIPTION) { PharmacyPrescription(nav, state.value) }
                composable(HEALTH_VAULT) { HealthVault(nav, state.value) }
                composable(APPOINTMENTS) { Appointments(nav, state.value) }
                composable(TELECONSULT) { Teleconsult(nav, state.value) }
                composable(AUDIT) { AuditScreen(nav, state.value) }
            }
        }
    }
}

@Composable
private fun Landing(nav: NavHostController, state: AppState) {
    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(42.dp))
        Icon(Icons.Default.HealthAndSafety, null, tint = Teal, modifier = Modifier.size(76.dp))
        Text("SwaasthyaSetu", color = Navy, fontSize = 34.sp, fontWeight = FontWeight.Bold)
        Text("Connected rural healthcare ecosystem", color = Color.Gray)
        Spacer(Modifier.height(24.dp))
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF7F4))) {
            Column(Modifier.padding(20.dp)) {
                Text("7 connected portals", color = Navy, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Text("Patient • ASHA • Doctor • Hospital • Ambulance • Pharmacy • Government", color = Color.DarkGray)
                Spacer(Modifier.height(10.dp))
                Text("Offline-first • Emergency SOS • Voice intake • AI scribe • Health Vault", color = Teal, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(18.dp))
        if (state.offline) OfflineBanner()
        Spacer(Modifier.height(18.dp))
        Button(
            onClick = { nav.navigate(SOS) },
            colors = ButtonDefaults.buttonColors(containerColor = Red),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth().height(72.dp)
        ) {
            Icon(Icons.Default.Emergency, contentDescription = "Emergency SOS", modifier = Modifier.size(30.dp))
            Spacer(Modifier.width(12.dp))
            Text("EMERGENCY SOS", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Spacer(Modifier.height(14.dp))
        OutlinedButton(
            onClick = { nav.navigate(LOGIN) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Icon(Icons.Default.Login, contentDescription = null)
            Spacer(Modifier.width(10.dp))
            Text("PORTAL LOGIN", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.weight(1f))
        Text("Functional Android prototype • SwaasthyaSetu", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
private fun Login(nav: NavHostController, role: MutableState<Role>) {
    var selected by remember { mutableStateOf(Role.PATIENT) }
    AppScaffold("SwaasthyaSetu Login", { nav.popBackStack() }) { p ->
        ScreenColumn(p) {
            Text("Choose your portal", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Navy)
            Text("Demo authentication is enabled for the prototype.", color = Color.Gray)
            Spacer(Modifier.height(16.dp))
            Role.values().forEach { r ->
                SelectableCard(roleLabel(r), selected == r, { selected = r }, icon = roleIcon(r))
            }
            Spacer(Modifier.height(14.dp))
            OutlinedTextField("demo@swaasthyasetu.in", {}, label = { Text("Email / Phone") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(10.dp))
            OutlinedTextField("demo1234", {}, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), singleLine = true, visualTransformation = PasswordVisualTransformation())
            Spacer(Modifier.height(18.dp))
            Button(onClick = { role.value = selected; nav.navigate(DASH) }, modifier = Modifier.fillMaxWidth().height(52.dp)) { Text("LOGIN") }
        }
    }
}

@Composable
private fun DashboardRouter(nav: NavHostController, role: Role, state: AppState) {
    when (role) {
        Role.PATIENT -> PatientPortal(nav, state)
        Role.ASHA -> AshaPortal(nav, state)
        Role.DOCTOR -> DoctorPortal(nav, state)
        Role.HOSPITAL -> HospitalPortal(nav, state)
        Role.AMBULANCE -> AmbulancePortal(nav, state)
        Role.PHARMACY -> PharmacyPortal(nav, state)
        Role.ADMIN -> AdminPortal(nav, state)
    }
}

@Composable
private fun PatientPortal(nav: NavHostController, state: AppState) {
    PortalScaffold("Patient Portal", nav, state) {
        HeroCard("Hello, Ananya", "ABHA: 11-2233-4455-6677", Teal, Icons.Default.Person)
        SectionTitle("Quick actions")
        ActionGrid(listOf(
            ActionItem("Emergency SOS", Icons.Default.Emergency, Red) { nav.navigate(SOS) },
            ActionItem("Health Vault", Icons.Default.FolderShared, Blue) { nav.navigate(HEALTH_VAULT) },
            ActionItem("Appointments", Icons.Default.Event, Purple) { nav.navigate(APPOINTMENTS) },
            ActionItem("Teleconsult", Icons.Default.VideoCall, Teal) { nav.navigate(TELECONSULT) }
        ))
        SectionTitle("Care status")
        StatusCard(if (state.referralCreated) "Referral active" else "No active referral", if (state.referralCreated) "Doctor review is available" else "Your care journey appears here", if (state.referralCreated) Amber else Green)
        InfoCard("Latest vitals", "BP 128/82 • Pulse 82 • SpO₂ 98%", "Last screening: Today")
        InfoCard("Prescription", if (state.prescriptionCreated) "Prescription ready" else "No new prescription", if (state.medicineDispensed) "Medicine dispensed" else "Pharmacy status pending")
        InfoCard(
            "Emergency",
            when {
                state.patientDroppedAtHospital -> "Arrived at hospital"
                state.ambulanceAssigned -> "Ambulance en route"
                state.sosActive -> "SOS active"
                else -> "Ready"
            },
            when {
                state.patientDroppedAtHospital -> "Hospital handover completed"
                state.ambulanceAssigned -> "Ramesh Kumar • ETA 9 min"
                else -> "One-tap emergency dispatch"
            }
        )
    }
}

@Composable
private fun AshaPortal(nav: NavHostController, state: AppState) {
    PortalScaffold("ASHA Worker Portal", nav, state) {
        HeroCard("ASHA Worker", "Assigned area: Kengeri Rural Cluster", Teal, Icons.Default.HealthAndSafety)
        StatusRow("Connectivity", if (state.offline) "Offline" else "Online", if (state.offline) Amber else Green)
        StatusRow("Sync", if (state.syncPending) "Pending" else "Synced", if (state.syncPending) Amber else Green)
        SectionTitle("Field workflow")
        ActionGrid(listOf(
            ActionItem("Register Patient", Icons.Default.PersonAdd, Blue) { nav.navigate(ASHA_REGISTER) },
            ActionItem("Record Vitals", Icons.Default.MonitorHeart, Red) { nav.navigate(ASHA_VITALS) },
            ActionItem("Referral", Icons.Default.Send, Purple) { nav.navigate(ASHA_REFERRAL) },
            ActionItem("Follow-up", Icons.Default.Home, Teal) { nav.navigate(ASHA_FOLLOW) },
            ActionItem("Voice Intake", Icons.Default.Mic, Amber) { nav.navigate(ASHA_VOICE) }
        ))
        SectionTitle("High-risk cases")
        InfoCard("Lakshmi Devi", "Pregnancy screening • HIGH RISK", "BP 150/96 • referral recommended")
        InfoCard("Ravi Kumar", "Fever screening • MODERATE", "Temperature 101.4°F • follow-up")
        SectionTitle("Offline-first")
        Text("Patient registration, vitals, screening, risk assessment, referrals and follow-up can be stored locally and synchronized when connectivity returns.", color = Color.DarkGray)
    }
}

@Composable
private fun DoctorPortal(nav: NavHostController, state: AppState) {
    PortalScaffold("Doctor Portal", nav, state) {
        HeroCard("Dr. Priya Sharma", "General Medicine • Swaasthya General Hospital", Blue, Icons.Default.MedicalServices)
        SectionTitle("Today")
        StatusRow("Appointments", "8", Blue)
        StatusRow("Pending referrals", if (state.referralCreated) "1 high-risk" else "0", if (state.referralCreated) Red else Green)
        ActionGrid(listOf(
            ActionItem("Referral Review", Icons.Default.Assignment, Red) { nav.navigate(DOCTOR_REFERRAL) },
            ActionItem("Consultation", Icons.Default.MedicalInformation, Blue) { nav.navigate(DOCTOR_CONSULT) },
            ActionItem("E-Prescription", Icons.Default.Description, Teal) { nav.navigate(DOCTOR_PRESCRIPTION) }
        ))
        InfoCard("AI Medical Scribe", "Speech → SOAP note", "Doctor reviews and confirms before saving")
        InfoCard("Patient", "Lakshmi Devi", if (state.risk == Risk.HIGH) "HIGH RISK • maternal screening" else "Routine review")
    }
}

@Composable
private fun HospitalPortal(nav: NavHostController, state: AppState) {
    PortalScaffold("Hospital Staff Portal", nav, state) {
        HeroCard("Swaasthya General Hospital", "Emergency & referral desk", Purple, Icons.Default.LocalHospital)
        SectionTitle("Capacity")
        StatusRow("General beds", "42 / 60 available", Green)
        StatusRow("ICU beds", "3 / 10 available", if (state.bedLocked) Amber else Green)
        StatusRow("Emergency", "6 / 12 bays", Blue)
        ActionGrid(listOf(
            ActionItem("Capacity", Icons.Default.Bed, Purple) { nav.navigate(HOSPITAL_CAPACITY) },
            ActionItem("Emergency desk", Icons.Default.Emergency, Red) { nav.navigate(AMBULANCE_TRIP) }
        ))
        InfoCard("Incoming referral", if (state.referralCreated) "Lakshmi Devi • HIGH RISK" else "No active referral", "Review patient → accept → prepare admission")
        InfoCard(
            "Ambulance",
            when {
                state.patientDroppedAtHospital -> "Patient arrived at hospital"
                state.ambulanceAssigned -> "En route"
                else -> "No active trip"
            },
            when {
                state.patientDroppedAtHospital -> "Handover completed • ready for admission"
                state.bedLocked -> "ICU bed reserved"
                else -> "Awaiting emergency request"
            }
        )
    }
}

@Composable
private fun AmbulancePortal(nav: NavHostController, state: AppState) {
    PortalScaffold("Ambulance Driver Portal", nav, state) {
        HeroCard("Ramesh Kumar", "Ambulance KA-01-AB1234", Red, Icons.Default.DirectionsCar)
        StatusRow(
            "Availability",
            if (state.ambulanceAssigned && !state.patientDroppedAtHospital) "On Trip" else "Available",
            if (state.ambulanceAssigned && !state.patientDroppedAtHospital) Amber else Green
        )
        SectionTitle("Emergency request")
        if (!state.sosActive) {
            EmptyState("No active emergency requests")
        } else if (!state.ambulanceAssigned) {
            InfoCard("INC-1024", "Chest Pain • Kengeri", "4.2 km away • ETA 9 min")
            Button(onClick = { state.ambulanceAssigned = true; state.sosActive = true; nav.navigate(AMBULANCE_TRIP) }, modifier = Modifier.fillMaxWidth()) { Text("ACCEPT EMERGENCY") }
        } else {
            InfoCard(
                "INC-1024",
                if (state.patientDroppedAtHospital) "Trip completed" else "You accepted this emergency",
                if (state.patientDroppedAtHospital) "Patient handover recorded at hospital" else "Patient can now see your live status"
            )
            Button(onClick = { nav.navigate(AMBULANCE_TRIP) }, modifier = Modifier.fillMaxWidth()) {
                Text(if (state.patientDroppedAtHospital) "VIEW COMPLETED TRIP" else "OPEN ACTIVE TRIP")
            }
        }
        SectionTitle("Dispatch flow")
        TimelineRow("SOS received", state.sosActive)
        TimelineRow("Ambulance assigned", state.ambulanceAssigned)
        TimelineRow("Patient pickup", state.ambulanceAssigned)
        TimelineRow("Hospital notified", state.ambulanceAssigned)
    }
}

@Composable
private fun PharmacyPortal(nav: NavHostController, state: AppState) {
    PortalScaffold("Pharmacy Portal", nav, state) {
        HeroCard("Swaasthya Pharmacy", "Connected dispensing desk", Teal, Icons.Default.LocalPharmacy)
        SectionTitle("Inventory")
        StatusRow("Medicines", "248 items", Green)
        StatusRow("Low stock", "12 items", Amber)
        StatusRow("Pending prescriptions", if (state.prescriptionCreated) "1" else "0", Blue)
        ActionGrid(listOf(ActionItem("Prescription", Icons.Default.Description, Blue) { nav.navigate(PHARMACY_PRESCRIPTION) }))
        InfoCard("Prescription", if (state.prescriptionCreated) "Amoxicillin + Paracetamol" else "No active prescription", if (state.medicineDispensed) "DISPENSED" else "Awaiting verification")
    }
}

@Composable
private fun AdminPortal(nav: NavHostController, state: AppState) {
    PortalScaffold("Government / Admin", nav, state) {
        HeroCard("Healthcare Network", "System monitoring & analytics", Navy, Icons.Default.Dashboard)
        SectionTitle("Network")
        MetricGrid(listOf("Patients\n1,284", "ASHA Workers\n86", "Doctors\n42", "Hospitals\n12", "Ambulances\n31", "Pharmacies\n27", "Referrals\n54", "Emergency\n6"))
        SectionTitle("System")
        StatusRow(
            "Emergency cases",
            when {
                state.patientDroppedAtHospital -> "1 arrived at hospital"
                state.sosActive -> "1 active"
                else -> "0 active"
            },
            if (state.patientDroppedAtHospital) Green else if (state.sosActive) Red else Green
        )
        StatusRow("High-risk cases", "7", Amber)
        StatusRow("Sync services", "Operational", Green)
        ActionGrid(listOf(ActionItem("Audit Trail", Icons.Default.History, Purple) { nav.navigate(AUDIT) }))
    }
}

@Composable
private fun SosScreen(nav: NavHostController, state: AppState) {
    var bystander by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf("Chest Pain") }
    AppScaffold("Emergency SOS", { nav.popBackStack() }) { p ->
        ScreenColumn(p) {
            Text("Request emergency assistance", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Navy)
            Text("Emergency dispatch can start without normal login.", color = Color.Gray)
            Spacer(Modifier.height(14.dp))
            StatusRow("GPS", "Available • Kengeri", Green)
            StatusRow("Network", if (state.offline) "Offline • queued" else "Online", if (state.offline) Amber else Green)
            Spacer(Modifier.height(12.dp))
            Text("What happened?", fontWeight = FontWeight.Bold)
            listOf("Accident / Major Injury", "Chest Pain", "Breathing Problem", "Pregnancy Emergency", "Other").forEach { SelectableCard(it, type == it, { type = it }) }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(bystander, { bystander = it }); Text("I am a bystander helping another person") }
            Spacer(Modifier.height(12.dp))
            if (!state.sosActive) {
                Button(
                    onClick = { state.sosActive = true; state.ambulanceAssigned = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) { Icon(Icons.Default.Emergency, null); Spacer(Modifier.width(8.dp)); Text("REQUEST AMBULANCE") }
                Spacer(Modifier.height(10.dp))
                Text(if (state.offline) "Offline: request is queued locally; fallback communication is simulated." else "Your request will be sent to nearby ambulance drivers.", color = Color.Gray, fontSize = 12.sp)
            } else if (!state.ambulanceAssigned) {
                StatusCard("REQUEST SENT", "Nearby ambulance drivers have been alerted. Waiting for a driver to accept.", Amber)
                Spacer(Modifier.height(12.dp))
                StatusRow("Dispatch status", "Finding nearby ambulance", Amber)
                Text("Stay on this screen. Driver and location details will appear once a driver accepts.", color = Color.Gray, fontSize = 12.sp)
            } else {
                StatusCard(
                    if (state.patientDroppedAtHospital) "ARRIVED AT HOSPITAL" else "AMBULANCE ACCEPTED",
                    if (state.patientDroppedAtHospital) "Driver confirmed your hospital handover." else "Ramesh Kumar is on the way • ETA 9 min",
                    Green
                )
                Spacer(Modifier.height(12.dp))
                Card(
                    Modifier.fillMaxWidth().height(170.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF5))
                ) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.DirectionsCar, null, tint = Red, modifier = Modifier.size(46.dp))
                            Text(if (state.patientDroppedAtHospital) "HOSPITAL HANDOVER COMPLETE" else "LIVE AMBULANCE LOCATION", color = Navy, fontWeight = FontWeight.Bold)
                            Text(if (state.patientDroppedAtHospital) "Patient has reached Swaasthya General Hospital" else "4.2 km away • En route from Kengeri Main Road", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                StatusRow("Driver", "Ramesh Kumar", Green)
                StatusRow("Vehicle", "KA-01-AB1234", Blue)
                StatusRow("Current status", if (state.patientDroppedAtHospital) "Patient handed over at hospital" else "En route to your location", Green)
                Text(if (state.patientDroppedAtHospital) "The hospital has been notified that the patient arrived." else "The location and status update automatically as the driver progresses.", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun AshaRegister(nav: NavHostController, state: AppState) {
    FormScreen(nav, "ASHA • Register Patient") {
        Field("Patient name", "Lakshmi Devi")
        Field("Age", "28")
        Field("Phone", "9876543210")
        Field("Village", "Kengeri Rural")
        Field("ABHA / Health ID", "11-2233-4455-6677")
        Field("Medical history", "No known allergies")
        SaveButton { state.syncPending = true; nav.popBackStack() }
        Text("Works offline: record is stored locally and marked Pending Sync.", color = Amber, fontSize = 12.sp)
    }
}

@Composable
private fun AshaVitals(nav: NavHostController, state: AppState) {
    var temp by remember { mutableStateOf("101.4") }
    FormScreen(nav, "ASHA • Vitals & Screening") {
        InfoCard("Patient", "Lakshmi Devi", "28 years • Maternal screening")
        Field("SpO₂", "96 %")
        Field("Blood pressure", "150 / 96 mmHg")
        Field("Pulse", "96 bpm")
        OutlinedTextField(temp, { temp = it }, label = { Text("Temperature °F") }, modifier = Modifier.fillMaxWidth())
        Field("Symptoms", "Fever, weakness, dizziness")
        RiskBadge(state.risk)
        Text("Prototype risk scoring uses entered vitals/symptoms as decision support.", color = Color.Gray, fontSize = 12.sp)
        SaveButton { state.syncPending = true; nav.popBackStack() }
    }
}

@Composable
private fun ReferralScreen(nav: NavHostController, state: AppState) {
    FormScreen(nav, "ASHA • Create Referral") {
        InfoCard("Patient", "Lakshmi Devi", "HIGH RISK • maternal screening")
        Field("Reason", "High BP + pregnancy symptoms")
        Field("Vitals", "BP 150/96 • Pulse 96 • SpO₂ 96%")
        Field("Priority", "HIGH")
        Field("Suggested facility", "Swaasthya General Hospital")
        SaveButton { state.referralCreated = true; state.syncPending = true; nav.popBackStack() }
    }
}

@Composable
private fun FollowUpScreen(nav: NavHostController, state: AppState) {
    FormScreen(nav, "ASHA • Follow-up Visit") {
        InfoCard("Patient", "Ravi Kumar", "Fever case • Moderate risk")
        Field("Visit notes", "Symptoms improving")
        Field("Medication adherence", "Taking Regularly")
        Field("Next visit", "Tomorrow")
        SaveButton { state.followUpSaved = true; state.syncPending = true; nav.popBackStack() }
    }
}

@Composable
private fun VoiceScreen(nav: NavHostController, state: AppState) {
    FormScreen(nav, "ASHA • Voice Intake") {
        Text("10 Indian vernacular languages", fontWeight = FontWeight.Bold, color = Navy)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("English", "Hindi", "Kannada").forEach { AssistChip(onClick = {}, label = { Text(it) }) } }
        Spacer(Modifier.height(12.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF7F4))) { Column(Modifier.padding(16.dp)) { Icon(Icons.Default.Mic, null, tint = Teal); Text("Listening / simulated transcription", fontWeight = FontWeight.Bold); Text(state.voiceText) } }
        Spacer(Modifier.height(12.dp))
        Field("Editable transcription", state.voiceText)
        SaveButton { state.syncPending = true; nav.popBackStack() }
        Text("Human confirmation is required before saving speech-derived information.", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
private fun DoctorReferral(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Doctor • Referral Review") {
        if (state.referralCreated) {
            InfoCard("HIGH RISK REFERRAL", "Lakshmi Devi", "ASHA • BP 150/96 • pregnancy symptoms")
            Field("Clinical review", "Requires hospital assessment")
            SaveButton { nav.popBackStack() }
        } else {
            EmptyState("No new referrals")
        }
    }
}

@Composable
private fun DoctorConsult(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Doctor • Consultation") {
        InfoCard("Patient", "Lakshmi Devi", "Referral: High risk")
        Field("Subjective", "Patient reports dizziness and weakness")
        Field("Objective", "BP 150/96, pulse 96, SpO₂ 96%")
        Field("Assessment", "Requires further evaluation")
        Field("Plan", "Hospital assessment and monitoring")
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF1EEFA))) { Column(Modifier.padding(14.dp)) { Text("AI Medical Scribe", fontWeight = FontWeight.Bold, color = Purple); Text("Speech → structured SOAP note → doctor review → save") } }
        SaveButton { state.consultationDone = true; nav.popBackStack() }
    }
}

@Composable
private fun PrescriptionScreen(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Doctor • E-Prescription") {
        InfoCard("Patient", "Lakshmi Devi", "Consultation completed")
        Field("Medicine", "Paracetamol 500 mg")
        Field("Dosage", "1 tablet")
        Field("Frequency", "Twice daily")
        Field("Duration", "3 days")
        Field("Instructions", "After food")
        SaveButton { state.prescriptionCreated = true; nav.popBackStack() }
    }
}

@Composable
private fun CapacityScreen(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Hospital • Capacity") {
        StatusRow("General beds", "42 available / 60", Green)
        StatusRow("ICU", if (state.bedLocked) "2 available / 10 • 1 locked" else "3 available / 10", if (state.bedLocked) Amber else Green)
        StatusRow("Emergency bays", "6 available / 12", Blue)
        Button(onClick = { state.bedLocked = true; state.auditCount++ }, modifier = Modifier.fillMaxWidth()) { Text(if (state.bedLocked) "ICU BED RESERVED" else "LOCK ICU BED") }
        Text("Prototype hospital desk can reserve a bed before ambulance arrival.", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
private fun AmbulanceTrip(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Ambulance • Active Trip") {
        StatusCard(
            when {
                state.patientDroppedAtHospital -> "PATIENT DROPPED AT HOSPITAL"
                state.bedLocked -> "ICU BED RESERVED"
                else -> "EN ROUTE TO PATIENT"
            },
            if (state.patientDroppedAtHospital) "Handover recorded • Swaasthya General Hospital" else "ETA 9 min • 4.2 km",
            if (state.patientDroppedAtHospital || state.bedLocked) Green else Blue
        )
        Card(Modifier.fillMaxWidth().height(180.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF5))) { Box(Modifier.fillMaxSize(), Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.Map, null, tint = Navy, modifier = Modifier.size(48.dp)); Text("LIVE TRACKING / ROUTE", color = Navy, fontWeight = FontWeight.Bold); Text("Ambulance → Patient → Hospital", color = Color.Gray) } } }
        TimelineRow("SOS received", state.sosActive)
        TimelineRow("Ambulance assigned", state.ambulanceAssigned)
        TimelineRow("Patient picked up", state.ambulanceAssigned)
        TimelineRow("Hospital notified", state.ambulanceAssigned)
        TimelineRow("Bed locked", state.bedLocked)
        TimelineRow("Patient dropped at hospital", state.patientDroppedAtHospital)
        if (!state.patientDroppedAtHospital) {
            Button(
                onClick = {
                    state.patientDroppedAtHospital = true
                    state.bedLocked = true
                    state.auditCount++
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("MARK PATIENT DROPPED AT HOSPITAL") }
        } else {
            StatusCard("TRIP COMPLETE", "All connected portals have been updated.", Green)
        }
    }
}

@Composable
private fun PharmacyPrescription(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Pharmacy • Prescription") {
        if (!state.prescriptionCreated) {
            EmptyState("Waiting for doctor prescription")
        } else {
            InfoCard("Patient", "Lakshmi Devi", "Doctor: Dr. Priya Sharma")
            InfoCard("Prescription", "Paracetamol 500 mg", "1 tablet • twice daily • 3 days")
            StatusRow("Stock", "Available", Green)
            Button(onClick = { state.medicineDispensed = true; state.auditCount++; nav.popBackStack() }, modifier = Modifier.fillMaxWidth()) { Text(if (state.medicineDispensed) "DISPENSED" else "VERIFY & DISPENSE") }
        }
    }
}

@Composable
private fun HealthVault(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Patient • Health Vault") {
        InfoCard("Identity", "Ananya Rao", "ABHA: 11-2233-4455-6677")
        InfoCard("Vitals", "BP 128/82 • Pulse 82 • SpO₂ 98%", "Recorded today")
        InfoCard("Consultations", if (state.consultationDone) "1 consultation" else "No recent consultation", "Doctor records")
        InfoCard("Prescriptions", if (state.prescriptionCreated) "1 active prescription" else "None", "E-prescription")
        InfoCard("Referrals", if (state.referralCreated) "1 active referral" else "None", "ASHA → Doctor → Hospital")
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))) { Column(Modifier.padding(15.dp)) { Text("Consent-based sharing", color = Navy, fontWeight = FontWeight.Bold); Text("Access is limited by role and recorded in the audit trail.", color = Color.Gray) } }
    }
}

@Composable
private fun Appointments(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Patient • Appointments") {
        InfoCard("Today 4:30 PM", "Dr. Priya Sharma", "General Medicine • Confirmed")
        InfoCard("Tomorrow 11:00 AM", "Swaasthya General Hospital", "Follow-up • Requested")
        Button(onClick = { nav.navigate(TELECONSULT) }, modifier = Modifier.fillMaxWidth()) { Text("OPEN TODAY'S TELECONSULT") }
    }
}

@Composable
private fun Teleconsult(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Teleconsultation") {
        Card(Modifier.fillMaxWidth().height(220.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF5))) { Box(Modifier.fillMaxSize(), Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.VideoCall, null, tint = Blue, modifier = Modifier.size(56.dp)); Text("VIDEO CONSULTATION", color = Navy, fontWeight = FontWeight.Bold); Text("WebRTC-ready prototype", color = Color.Gray) } } }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { IconButton(onClick = {}) { Icon(Icons.Default.Mic, null) }; IconButton(onClick = {}) { Icon(Icons.Default.Videocam, null) }; IconButton(onClick = {}) { Icon(Icons.Default.VolumeUp, null) } }
        Button(onClick = { nav.navigate(DOCTOR_CONSULT) }, modifier = Modifier.fillMaxWidth()) { Text("END & OPEN CONSULTATION") }
        Text("If real WebRTC is unavailable, this screen represents the functional prototype flow.", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
private fun AuditScreen(nav: NavHostController, state: AppState) {
    FormScreen(nav, "Government • Audit Trail") {
        Text("Recent activity", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy)
        listOf(
            "ASHA viewed Lakshmi Devi • 2 min ago",
            "Vitals recorded • 4 min ago",
            "High-risk referral created • 5 min ago",
            "Doctor reviewed referral • 8 min ago",
            "Prescription created • 12 min ago",
            "Pharmacy accessed prescription • 15 min ago"
        ).forEach { InfoCard("Audit event", it, "Role-based access • consent-aware") }
        Text("Total events in demo: ${state.auditCount}", color = Color.Gray)
    }
}

@Composable
private fun PortalScaffold(title: String, nav: NavHostController, state: AppState, content: @Composable ColumnScope.() -> Unit) {
    AppScaffold(title, { nav.popBackStack() }, actions = {
        IconButton(onClick = { state.offline = !state.offline }) { Icon(if (state.offline) Icons.Default.SignalWifiOff else Icons.Default.Wifi, null, tint = if (state.offline) Amber else Green) }
    }) { p ->
        ScreenColumn(p) {
            if (state.offline) OfflineBanner()
            Spacer(Modifier.height(6.dp))
            content()
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormScreen(nav: NavHostController, title: String, content: @Composable ColumnScope.() -> Unit) {
    AppScaffold(title, { nav.popBackStack() }) { p -> ScreenColumn(p) { content() } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppScaffold(title: String, onBack: (() -> Unit)? = null, actions: @Composable RowScope.() -> Unit = {}, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(title, color = Navy, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis) }, navigationIcon = { if (onBack != null) IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Navy) } }, actions = actions, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)) }) { p -> content(p) }
}

@Composable
private fun ScreenColumn(p: PaddingValues, content: @Composable ColumnScope.() -> Unit) { Column(Modifier.fillMaxSize().padding(p).padding(horizontal = 20.dp).verticalScroll(rememberScrollState()), content = content) }

private data class ActionItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val color: Color, val click: () -> Unit)

@Composable
private fun ActionGrid(items: List<ActionItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.chunked(2).forEach { row -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) { row.forEach { item -> Card(onClick = item.click, modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White)) { Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) { Icon(item.icon, null, tint = item.color, modifier = Modifier.size(30.dp)); Spacer(Modifier.height(6.dp)); Text(item.label, fontWeight = FontWeight.SemiBold, fontSize = 13.sp) } } }; if (row.size == 1) Spacer(Modifier.weight(1f)) } }
    }
}

@Composable
private fun HeroCard(title: String, subtitle: String, color: Color, icon: androidx.compose.ui.graphics.vector.ImageVector) { Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp)) { Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = color, modifier = Modifier.size(46.dp)); Spacer(Modifier.width(14.dp)); Column { Text(title, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Navy); Text(subtitle, color = Color.Gray, fontSize = 13.sp) } } } }

@Composable private fun SectionTitle(t: String) { Spacer(Modifier.height(18.dp)); Text(t, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Navy); Spacer(Modifier.height(8.dp)) }

@Composable private fun InfoCard(label: String, value: String, sub: String) { Card(Modifier.fillMaxWidth().padding(vertical = 5.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) { Column(Modifier.padding(14.dp)) { Text(label.uppercase(), color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold); Text(value, color = Navy, fontSize = 16.sp, fontWeight = FontWeight.SemiBold); Text(sub, color = Color.Gray, fontSize = 12.sp) } } }

@Composable private fun StatusCard(title: String, subtitle: String, color: Color) { Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = color.copy(alpha = .10f))) { Column(Modifier.padding(16.dp)) { Text(title, color = color, fontWeight = FontWeight.Bold); Text(subtitle, color = Color.DarkGray, fontSize = 13.sp) } } }

@Composable private fun StatusRow(label: String, value: String, color: Color) { Row(Modifier.fillMaxWidth().padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(9.dp).background(color, RoundedCornerShape(50))); Spacer(Modifier.width(10.dp)); Text(label, Modifier.weight(1f), color = Color.DarkGray); Text(value, fontWeight = FontWeight.Bold, color = Navy) } }

@Composable private fun RiskBadge(risk: Risk) { val c = when(risk){Risk.LOW->Green;Risk.MODERATE->Amber;Risk.HIGH->Red}; StatusCard("${risk.name} RISK", if(risk==Risk.HIGH) "Further medical attention recommended" else "Continue monitoring", c) }

@Composable private fun SelectableCard(text: String, selected: Boolean, click: () -> Unit, icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Default.CheckCircle) { Card(onClick = click, Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = if(selected) Color(0xFFEAF3FF) else Color.White)) { Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = if(selected) Blue else Color.Gray); Spacer(Modifier.width(12.dp)); Text(text, Modifier.weight(1f), fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal); if(selected) Icon(Icons.Default.Check, null, tint = Blue) } } }

@Composable private fun Field(label: String, value: String) { OutlinedTextField(value, {}, label = { Text(label) }, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), minLines = 1) }
@Composable private fun SaveButton(click: () -> Unit) { Spacer(Modifier.height(12.dp)); Button(onClick = click, Modifier.fillMaxWidth().height(50.dp)) { Icon(Icons.Default.Save, null); Spacer(Modifier.width(8.dp)); Text("SAVE") } }
@Composable private fun EmptyState(text: String) { Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) { Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.Inbox, null, tint = Color.Gray, modifier = Modifier.size(42.dp)); Text(text, color = Color.Gray) } } }
@Composable private fun OfflineBanner() { Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF6DF))) { Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.SignalWifiOff, null, tint = Amber); Spacer(Modifier.width(8.dp)); Column { Text("OFFLINE MODE", fontWeight = FontWeight.Bold, color = Amber); Text("Critical field workflows continue locally; sync resumes later.", fontSize = 12.sp, color = Color.DarkGray) } } } }
@Composable private fun TimelineRow(text: String, done: Boolean) { Row(Modifier.fillMaxWidth().padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) { Icon(if(done) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, null, tint = if(done) Green else Color.LightGray); Spacer(Modifier.width(10.dp)); Text(text, color = if(done) Navy else Color.Gray) } }
@Composable private fun MetricGrid(values: List<String>) { Column { values.chunked(2).forEach { row -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) { row.forEach { v -> Card(Modifier.weight(1f).padding(vertical = 4.dp)) { Text(v, Modifier.padding(16.dp), color = Navy, fontWeight = FontWeight.Bold) } }; if(row.size==1) Spacer(Modifier.weight(1f)) } } } }

private fun roleLabel(r: Role) = when(r){Role.PATIENT->"Patient";Role.ASHA->"ASHA Worker";Role.DOCTOR->"Doctor";Role.HOSPITAL->"Hospital Staff";Role.AMBULANCE->"Ambulance Driver";Role.PHARMACY->"Pharmacy";Role.ADMIN->"Government / Admin"}
private fun roleIcon(r: Role) = when(r){Role.PATIENT->Icons.Default.Person;Role.ASHA->Icons.Default.HealthAndSafety;Role.DOCTOR->Icons.Default.MedicalServices;Role.HOSPITAL->Icons.Default.LocalHospital;Role.AMBULANCE->Icons.Default.DirectionsCar;Role.PHARMACY->Icons.Default.LocalPharmacy;Role.ADMIN->Icons.Default.Dashboard}
