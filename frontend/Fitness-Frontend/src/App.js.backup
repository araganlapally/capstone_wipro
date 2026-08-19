import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Goals from "./pages/Goals";
import Profile from "./pages/Profile";
import Workouts from "./pages/Workouts";
import Nutrition from "./pages/Nutrition";

<Route path="/profile" element={<Profile />} />

function App() {
  return (
    <Router>
      <Routes>

        {/* AUTH */}
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* APP */}
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/goals" element={<Goals />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/workouts" element={<Workouts />} />
        <Route path="/nutrition" element={<Nutrition />} />
      </Routes>
    </Router>
  );
}

export default App;