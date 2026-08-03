import { useState } from "react";
import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Stack,
    Box,
} from "@mui/material";
import { useNavigate, Link } from "react-router-dom";
import toast, { Toaster } from "react-hot-toast";
import { login } from "../../services/authService";
import "./Login.css";

function Login() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });

    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            setLoading(true);

            const response = await login(formData);

            localStorage.setItem(
                "accessToken",
                response.accessToken
            );

            localStorage.setItem(
                "refreshToken",
                response.refreshToken
            );

            localStorage.setItem(
                "name",
                response.name
            );

            localStorage.setItem(
                "role",
                response.role
            );

            toast.success("Login Successful");

            navigate("/dashboard");

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Invalid email or password"
            );

        } finally {

            setLoading(false);

        }

    };

    return (

        <>

            <Toaster position="top-right" />

            <div className="login-page">

                <Container maxWidth="sm">

                    <Paper
                        elevation={0}
                        className="login-card"
                    >

                        <div className="login-logo">
                            DP
                        </div>

                        <Typography className="login-title">
                            Welcome Back
                        </Typography>

                        <Typography className="login-subtitle">
                            Sign in to continue to DevPulse
                        </Typography>

                        <Stack
                            component="form"
                            spacing={3}
                            onSubmit={handleSubmit}
                        >

                            <TextField
                                className="login-input"
                                label="Email Address"
                                name="email"
                                type="email"
                                fullWidth
                                required
                                value={formData.email}
                                onChange={handleChange}
                            />

                            <TextField
                                className="login-input"
                                label="Password"
                                name="password"
                                type="password"
                                fullWidth
                                required
                                value={formData.password}
                                onChange={handleChange}
                            />

                            <Box
                                display="flex"
                                justifyContent="flex-end"
                            >

                                <Link
                                    to="/forgot-password"
                                    className="login-link"
                                >
                                    Forgot Password?
                                </Link>

                            </Box>

                            <Button
                                type="submit"
                                variant="contained"
                                className="login-btn"
                                disabled={loading}
                            >
                                {
                                    loading
                                        ? "Logging in..."
                                        : "Login"
                                }
                            </Button>

                            <Typography
                                align="center"
                            >

                                Don't have an account?{" "}

                                <Link
                                    to="/register"
                                    className="login-link"
                                >
                                    Register
                                </Link>

                            </Typography>

                        </Stack>

                    </Paper>

                </Container>

            </div>

        </>

    );

}

export default Login;