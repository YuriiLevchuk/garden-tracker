import { Route, Routes } from "react-router-dom";
import { LandingPage } from "@/pages/landing/Landing";
import { LoginPage } from "@/pages/auth/Login";
import { RegisterPage } from "@/pages/auth/Register";
import { GardenPage } from "@/pages/garden/Garden";

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/garden" element={<GardenPage />} />
    </Routes>
  );
};

export default App;
