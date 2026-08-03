import { Routes, Route, Navigate } from "react-router-dom";

import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import ForgotPassword from "../pages/auth/ForgotPassword";
import ResetPassword from "../pages/auth/ResetPassword";

import Dashboard from "../pages/dashboard/Dashboard";
import Tasks from "../pages/tasks/Tasks";
import Profile from "../pages/profile/Profile";

import NotFound from "../pages/NotFound";
import ProtectedRoute from "./ProtectedRoute";
import KanbanBoard from "../pages/kanban/KanbanBoard";
function AppRoutes() {

    return (

        <Routes>

            <Route
                path="/"
                element={<Navigate to="/login" replace />}
            />

            <Route
                path="/login"
                element={<Login />}
            />

            <Route
                path="/register"
                element={<Register />}
            />

            <Route
                path="/forgot-password"
                element={<ForgotPassword />}
            />

            <Route
                path="/reset-password"
                element={<ResetPassword />}
            />

            <Route
                path="/dashboard"
                element={
                    <ProtectedRoute>
                        <Dashboard />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/tasks"
                element={
                    <ProtectedRoute>
                        <Tasks />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/profile"
                element={
                    <ProtectedRoute>
                        <Profile />
                    </ProtectedRoute>
                }
            />

            <Route
                path="*"
                element={<NotFound />}
            />

            <Route
                path="/kanban"
                element={
                    <ProtectedRoute>
                        <KanbanBoard />
                    </ProtectedRoute>
                }
            />

        </Routes>

    );

}

export default AppRoutes;