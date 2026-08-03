import { useEffect, useState } from "react";
import {
    Avatar,
    Button,
    Card,
    CardContent,
    Container,
    Stack,
    TextField,
    Typography,
} from "@mui/material";

import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

import MainLayout from "../../components/layout/MainLayout";

import {
    getProfile,
    updateProfile,
    uploadProfileImage,
    deleteAccount,
} from "../../services/profileService";

function Profile() {

    const navigate = useNavigate();

    const [user, setUser] = useState({
        name: "",
        email: "",
        role: "",
        profileImage: "",
    });

    useEffect(() => {
        loadProfile();
    }, []);

    const loadProfile = async () => {

        try {

            const data = await getProfile();

            setUser(data);

            localStorage.setItem("name", data.name);

            localStorage.setItem(
                "profileImage",
                data.profileImage || ""
            );

            window.dispatchEvent(
                new Event("profileUpdated")
            );

        } catch (e) {

            console.log(e);

        }

    };

    const handleChange = (e) => {

        setUser({
            ...user,
            [e.target.name]: e.target.value,
        });

    };

    const handleUpdate = async () => {

        try {

            await updateProfile({
                name: user.name,
                email: user.email,
            });

            toast.success("Profile Updated");

            loadProfile();

        } catch (e) {

            toast.error("Update Failed");

        }

    };



    const handleImageUpload = async (e) => {

        const file = e.target.files[0];

        if (!file) return;

        try {

            await uploadProfileImage(file);

            toast.success("Image Uploaded");

            loadProfile();

        } catch (e) {

            toast.error("Upload Failed");

        }

    };

    const handleDeleteAccount = async () => {

        if (!window.confirm("Delete your account?")) {
            return;
        }

        try {

            await deleteAccount();

            localStorage.clear();

            toast.success("Account Deleted");

            navigate("/login");

        } catch (e) {

            toast.error("Delete Failed");

        }

    };

    return (

        <MainLayout>

            <Container maxWidth="sm">

                <Card elevation={4}>

                    <CardContent>

                        <Stack
                            spacing={3}
                            alignItems="center"
                        >

                            <Avatar
                                src={user.profileImage}
                                sx={{
                                    width: 120,
                                    height: 120,
                                }}
                            />

                            <TextField
                                fullWidth
                                label="Name"
                                name="name"
                                value={user.name}
                                onChange={handleChange}
                            />

                            <TextField
                                fullWidth
                                label="Email"
                                name="email"
                                value={user.email}
                                onChange={handleChange}
                            />

                            <TextField
                                fullWidth
                                disabled
                                label="Role"
                                value={user.role}
                            />

                            <Stack
                                direction="row"
                                spacing={2}
                            >

                                <Button
                                    variant="contained"
                                    onClick={handleUpdate}
                                >
                                    Update Profile
                                </Button>

                                <Button
                                    variant="outlined"
                                    component="label"
                                >
                                    Upload Image

                                    <input
                                        hidden
                                        type="file"
                                        onChange={handleImageUpload}
                                    />

                                </Button>

                            </Stack>

                            <Button
                                color="error"
                                variant="contained"
                                onClick={handleDeleteAccount}
                            >
                                Delete Account
                            </Button>

                        </Stack>

                    </CardContent>

                </Card>

            </Container>

        </MainLayout>

    );

}

export default Profile;