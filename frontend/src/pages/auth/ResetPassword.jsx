import { useState } from "react";
import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Stack,
} from "@mui/material";

import { useNavigate, useSearchParams } from "react-router-dom";
import toast, { Toaster } from "react-hot-toast";

import { resetPassword } from "../../services/authService";
import "./Login.css";

function ResetPassword() {

    const navigate = useNavigate();

    const [searchParams] = useSearchParams();

    const token = searchParams.get("token");

    const [form, setForm] = useState({
        newPassword: "",
        confirmPassword: "",
    });

    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        if (form.newPassword !== form.confirmPassword) {

            toast.error("Passwords do not match");

            return;

        }

        try {

            setLoading(true);

            await resetPassword({

                token,

                newPassword: form.newPassword,

            });

            toast.success(
                "Password Reset Successful"
            );

            setTimeout(() => {

                navigate("/login");

            }, 1200);

        } catch (error) {

            toast.error(

                error.response?.data?.message ||

                "Reset Password Failed"

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
                            🔑
                        </div>

                        <Typography
                            className="login-title"
                        >
                            Reset Password
                        </Typography>

                        <Typography
                            className="login-subtitle"
                        >
                            Create a new secure password.
                        </Typography>

                        <Stack
                            component="form"
                            spacing={3}
                            onSubmit={handleSubmit}
                        >

                            <TextField
                                className="login-input"
                                label="New Password"
                                name="newPassword"
                                type="password"
                                fullWidth
                                required
                                value={form.newPassword}
                                onChange={handleChange}
                            />

                            <TextField
                                className="login-input"
                                label="Confirm Password"
                                name="confirmPassword"
                                type="password"
                                fullWidth
                                required
                                value={form.confirmPassword}
                                onChange={handleChange}
                            />

                            <Button
                                type="submit"
                                variant="contained"
                                className="login-btn"
                                disabled={loading}
                            >

                                {
                                    loading
                                        ? "Updating..."
                                        : "Reset Password"
                                }

                            </Button>

                        </Stack>

                    </Paper>

                </Container>

            </div>

        </>

    );

}

export default ResetPassword;