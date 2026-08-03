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
import { Link } from "react-router-dom";
import toast, { Toaster } from "react-hot-toast";

import { forgotPassword } from "../../services/authService";
import "./Login.css";

function ForgotPassword() {

    const [email, setEmail] = useState("");

    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            setLoading(true);

            await forgotPassword({
                email,
            });

            toast.success(
                "Password reset link sent to your email."
            );

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to send reset link"
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
                            🔒
                        </div>

                        <Typography
                            className="login-title"
                        >
                            Forgot Password
                        </Typography>

                        <Typography
                            className="login-subtitle"
                        >
                            Enter your registered email to receive a password reset link.
                        </Typography>

                        <Stack
                            component="form"
                            spacing={3}
                            onSubmit={handleSubmit}
                        >

                            <TextField
                                className="login-input"
                                label="Email Address"
                                type="email"
                                fullWidth
                                required
                                value={email}
                                onChange={(e) =>
                                    setEmail(e.target.value)
                                }
                            />

                            <Button
                                type="submit"
                                variant="contained"
                                className="login-btn"
                                disabled={loading}
                            >
                                {
                                    loading
                                        ? "Sending..."
                                        : "Send Reset Link"
                                }
                            </Button>

                            <Box
                                textAlign="center"
                            >

                                <Link
                                    to="/login"
                                    className="login-link"
                                >
                                    ← Back to Login
                                </Link>

                            </Box>

                        </Stack>

                    </Paper>

                </Container>

            </div>

        </>

    );

}

export default ForgotPassword;