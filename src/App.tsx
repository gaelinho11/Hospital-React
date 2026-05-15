import { createBrowserRouter, RouterProvider } from "react-router";
import Home from "./pages/home";
import CreateRoomPage from "./pages/CreateRoomPage";
import CreatePatientPage from "./pages/CreatePatientPage";
import RoomStatePage from "./pages/RoomStatePage";
import AsignarRoomPage from "./pages/AsignarRoomPage";
import AltaPatientPage from "./pages/AltaPatientPage";
import HistorialPage from "./pages/HistorialPage";

/*
import CreateUserPage from "./pages/CreateUserPage";
import CreateTaskPage from "./pages/CreateTaskPage";
import UserTasksPage from "./pages/UserTaskPage";
import PendingTaskPage from "./pages/PendingTaskPage";
*/


const router = createBrowserRouter([
  { path: "/", element: <Home /> },
  
  { path: "/rooms/new", element: <CreateRoomPage /> },
  { path: "/patients/new", element: <CreatePatientPage /> },
  { path: "/rooms/status", element: <RoomStatePage /> },
  { path: "/patients/asignation", element: <AsignarRoomPage /> },
  { path: "/patients/alta", element: <AltaPatientPage /> },
  { path: "/patients/log", element: <HistorialPage /> },




  /*
  { path: "/tasks/new", element: <CreateTaskPage /> },
  { path: "/tasks/search", element: <UserTasksPage /> },
  { path: "/tasks/pending", element: <PendingTaskPage /> },
  */
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;