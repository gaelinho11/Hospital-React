import { useState } from "react";
import { useNavigate } from "react-router";
import { crearRoom } from "../services/api";
import type { Room } from "../types/models";
 
function CreateRoomPage() {
  const navigate = useNavigate();
 
  const [formData, setFormData] = useState<Room>({
    id: 0,
    monitored: false,
    roomType: "normal"
  });
 
  const [errors, setErrors] = useState<string[]>([]);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);
 
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
 
    if (formData.id == 0) {
      newErrors.push("El Numero d'habitació és obligatori.");
    }
 
    return newErrors;
  };
 
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors([]);
    setSuccessMsg(null);
 
    const validationErrors = validate();
    if (validationErrors.length > 0) {
      setErrors(validationErrors);
      return;
    }
 
    try {
      const data = await crearRoom(formData);
      setSuccessMsg("Habitació " +data.id+ " creada correctament!");

      setFormData({ id: 0, roomType: "", monitored: false });
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
            Alta d'habitació
          </h2>
 
          {errors.length > 0 && (
            <div className="alert alert-danger mt-3">
              {errors.map((err, i) => (
                <div key={i}>{err}</div>
              ))}
            </div>
          )}
 
          {successMsg && (
            <div className="alert alert-success mt-3">
              {successMsg}
            </div>
          )}
 
          <div className="card mt-3">
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="id" className="form-label">
                    Número d'habitació
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="id"
                    name="id"
                    maxLength={4} //li he posat 4 perque a la base de dades es el limit.
                    value={formData.id === 0 ? "" : formData.id} //he afegit aixo perque es netegi al enviar
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                    <label className="form-label">Tipus</label>
                    <select 
                        className="form-select" 
                        name="roomType" 
                        value={formData.roomType} 
                        onChange={handleChange}
                    >
                        <option value="normal">NORMAL</option>
                        <option value="uci">UCI</option>
                        <option value="quirurgica">QUIRURGICA</option>
                        <option value="aillament">AÏLLAMENT</option>
                    </select>
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
                    <label className="form-label">  Sistema de monotorització</label>
                </div>
 
                <div className="d-flex gap-2">
                  <button type="submit" className="btn btn-success">
                    <i className="bi bi-check-lg me-1"></i>
                    Donar d'alta
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
 
export default CreateRoomPage;