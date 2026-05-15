import { useState } from "react";
import { useNavigate } from "react-router";
import { crearPatient } from "../services/api";
import type { Patient } from "../types/models";
 
function CreatePatientPage() {
  const navigate = useNavigate();
 
  const [formData, setFormData] = useState<Patient>({
    nif: "",
    fullName: "",
    age: 0,
    diagnosis: "",
    monitored: false,
    urgency: 0,
  });
 
  const [errors, setErrors] = useState<string[]>([]);
  const [success, setSuccess] = useState(false);
 
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    //he hagut de calcular primer les variables perque sino el checkbox no em passaba res
    const { name, type } = e.target;
    const value = type === 'checkbox' 
      ? (e.target as HTMLInputElement).checked 
      : e.target.value;
    setFormData({ ...formData, [name]: value });
  };
 
  const validate = (): string[] => {
    const newErrors: string[] = [];
 
    if (!formData.nif.trim()) {
      newErrors.push("El NIF és obligatori.");
    } else if (formData.nif.length > 9) {
      newErrors.push("El NIF no pot tenir més de 9 caràcters.");
    }
 
    if (!formData.fullName.trim()) {
      newErrors.push("El nom és obligatori.");
    } else if (formData.fullName.length > 50) {
      newErrors.push("El nom no pot tenir més de 50 caràcters.");
    }
    if (!formData.diagnosis.trim()) {
      newErrors.push("El diagnostic és obligatori.");
    } else if (formData.diagnosis.length > 50) {
      newErrors.push("El diagnostic no pot tenir més de 50 caràcters.");
    }

 
    return newErrors;
  };
 
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors([]);
    setSuccess(false);
 
    const validationErrors = validate();
    if (validationErrors.length > 0) {
      setErrors(validationErrors);
      return;
    }
 
    try {
      await crearPatient(formData);
      setSuccess(true);
      setFormData({ nif: "",fullName: "",age: 0,diagnosis: "",monitored: false,urgency: 0,});
    } catch (error) {
      setErrors([error instanceof Error ? error.message : "S'ha produït un error desconegut"]);
    }
  };
 
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h2>
            <i className="bi bi-person-plus me-2"></i>
            Registre de Pacient
          </h2>
 
          {errors.length > 0 && (
            <div className="alert alert-danger mt-3">
              {errors.map((err, i) => (
                <div key={i}>{err}</div>
              ))}
            </div>
          )}
 
          {success && (
            <div className="alert alert-success mt-3">
              Pacient {formData.nif} registrat amb exit
            </div>
          )}
 
          <div className="card mt-3">
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="nif" className="form-label">
                    NIF
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="nif"
                    name="nif"
                    maxLength={9}
                    value={formData.nif}
                    onChange={handleChange}
                  />
                  <div className="form-text">Màxim 9 caràcters</div>
                </div>

                <div className="mb-3">
                  <label htmlFor="name" className="form-label">
                    Nom Complet
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="fullName"
                    name="fullName"
                    maxLength={50}
                    value={formData.fullName}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="id" className="form-label">
                    Edat
                  </label>
                  <input
                    required
                    type="number"
                    className="form-control"
                    id="age"
                    name="age"
                    minLength={0}
                    maxLength={3}
                    value={formData.age === 0 ? "" : formData.age} //he afegit aixo perque es netegi al enviar
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="name" className="form-label">
                    Diagnostic
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="diagnosis"
                    name="diagnosis"
                    maxLength={50}
                    value={formData.diagnosis}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                    <input 
                        className="form-check-input" 
                        type="checkbox" 
                        name="monitored"
                        id="monitored" 
                        checked={formData.monitored}
                        onChange={handleChange} 
                    />
                    <label className="form-label"> Monitoritzat</label>
                </div>

                <div className="mb-3">
                    <label className="form-label">Nivell d'urgència</label>
                    <select 
                        className="form-select" 
                        name="urgency" 
                        value={formData.urgency} 
                        onChange={handleChange}
                    >
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>
                
                
 
                <div className="d-flex gap-2">
                  <button type="submit" className="btn btn-success">
                    <i className="bi bi-check-lg me-1"></i>
                    Registrar pacient
                  </button>
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={() => navigate("/")}
                  >
                    Tornar
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
 
export default CreatePatientPage;