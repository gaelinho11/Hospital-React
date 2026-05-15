import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { assignarHabitació, llistarPatientsLliures } from "../services/api";
import type { Patient } from "../types/models";
 
function AsignarRoomPage() {
  const navigate = useNavigate();
  const [patients, setPatients] = useState<Patient[]>([]);
  const [selectedNif, setSelectedNif] = useState("");

  const [errors, setErrors] = useState<string | null>(null);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);
 
  useEffect(() => {
    llistarPatientsLliures()
      .then((data) => setPatients(data))
      .catch(() => setErrors("No s'han pogut carregar els usuaris."));
  }, []);
 
  
 
  
 
  
 
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors(null);
    setSuccessMsg(null);
    
    if (!selectedNif) {
        setErrors("Si us plau, selecciona un pacient.");
        return;
    }

    try {
      const data = await assignarHabitació(selectedNif);
      setSuccessMsg("Pacient assignat correctament a l'habitació " + data.id + " de tipus " + data.roomType); //asigno aqui a dalt el success message que em sembla més comode, despres ja el mostro a baix (faig el mateix amb el error)
      llistarPatientsLliures()
      .then((data) => setPatients(data))
      .catch(() => setErrors("No s'han pogut carregar els usuaris."));
    } catch (error) {
      setErrors(error instanceof Error ? error.message : "No hi ha habitacions lliures amb monitorització disponibles");
    }
  };
 
  return (
    
    <div className="container mt-5">
      <button className="btn btn-outline-secondary mb-3 btn-sm" onClick={() => navigate("/")}>
        ← Tornar a l'inici
      </button>
      {patients.length === 0 && !errors ? (
        <div className="alert alert-info shadow-sm border-0 mt-3">
          <i className="bi bi-info-circle-fill me-2"></i>
          No hi ha pacients pendents d'assignació.
        </div>
      ) : (
      <div className="card shadow-sm">
        {/* Capçalera groga com a la foto */}
        <div className="card-header bg-warning py-3">
          <h5 className="mb-0 fw-bold">
            <i className="bi bi-lightning-fill me-2"></i>
            Assignació Automàtica d'Habitació
          </h5>
        </div>
        
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label className="form-label fw-bold">Pacient</label>
              <select
                className="form-select"
                value={selectedNif}
                onChange={(e) => setSelectedNif(e.target.value)}
              >
                <option value="">-- Selecciona un pacient --</option>
                {patients.map((patient) => (
                  <option key={patient.nif} value={patient.nif}>
                    {patient.fullName} - {patient.monitored ? "🔴 Requereix Monitor" : "Estàndard"}
                  </option>
                ))}
              </select>
            </div>

            <button type="submit" className="btn btn-warning fw-bold mb-3">
              Assignar Habitació Ara
            </button>

            <p className="text-muted small">
              El sistema cercarà automàticament la millor habitació disponible segons els requeriments de monitorització.
            </p>


            {errors && (//aqui mostro els missatges que he assignat a dalt, tant de error com de success
              <div className="alert alert-danger border-0 shadow-sm mt-2">
                {errors}
              </div>
            )}

            {successMsg && (
              <div className="alert alert-success border-0 shadow-sm mt-2">
                <i className="bi bi-check-circle-fill me-2"></i>
                {successMsg}
              </div>
            )}
          </form>
        </div>
      </div>
      )}
    </div>
          
  );
}
 
export default AsignarRoomPage;