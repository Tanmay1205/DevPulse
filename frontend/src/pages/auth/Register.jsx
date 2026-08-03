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
import { Link, useNavigate } from "react-router-dom";
import toast, { Toaster } from "react-hot-toast";
import { register } from "../../services/authService";
import "./Login.css";

function Register() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
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

        if (formData.password !== formData.confirmPassword) {

            toast.error("Passwords do not match");

            return;

        }

        try {

            setLoading(true);

            await register({
                name: formData.name,
                email: formData.email,
                password: formData.password,
            });

            toast.success("Registration Successful");

            setTimeout(() => {

                navigate("/login");

            }, 1200);

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Registration Failed"
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
                            Create Account
                        </Typography>

                        <Typography className="login-subtitle">
                            Join DevPulse today 🚀
                        </Typography>

                        <Stack
                            component="form"
                            spacing={3}
                            onSubmit={handleSubmit}
                        >

                            <TextField
                                className="login-input"
                                label="Full Name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                fullWidth
                                required
                            />

                            <TextField
                                className="login-input"
                                label="Email Address"
                                name="email"
                                type="email"
                                value={formData.email}
                                onChange={handleChange}
                                fullWidth
                                required
                            />

                            <TextField
                                className="login-input"
                                label="Password"
                                name="password"
                                type="password"
                                value={formData.password}
                                onChange={handleChange}
                                fullWidth
                                required
                            />

                            <TextField
                                className="login-input"
                                label="Confirm Password"
                                name="confirmPassword"
                                type="password"
                                value={formData.confirmPassword}
                                onChange={handleChange}
                                fullWidth
                                required
                            />

                            <Button
                                type="submit"
                                variant="contained"
                                className="login-btn"
                                disabled={loading}
                            >
                                {
                                    loading
                                        ? "Creating Account..."
                                        : "Register"
                                }
                            </Button>

                            <Box textAlign="center">

                                <Typography>

                                    Already have an account?{" "}

                                    <Link
                                        to="/login"
                                        className="login-link"
                                    >
                                        Login
                                    </Link>

                                </Typography>

                            </Box>

                        </Stack>

                    </Paper>

                </Container>

            </div>

        </>

    );

}

export default Register;