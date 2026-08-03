import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Avatar,
    IconButton,
    Badge,
    Tooltip,
} from "@mui/material";

import NotificationsIcon from "@mui/icons-material/Notifications";
import LogoutIcon from "@mui/icons-material/Logout";
import NotificationMenu from "../notifications/NotificationMenu";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

import "./Navbar.css";

function Navbar() {

    const navigate = useNavigate();

    const [profileImage, setProfileImage] = useState(
        localStorage.getItem("profileImage") || ""
    );

    const [name, setName] = useState(
        localStorage.getItem("name") || "User"
    );

    const [anchorEl,setAnchorEl]=useState(null);

    const open=Boolean(anchorEl);

    const handleOpen=(e)=>{

        setAnchorEl(e.currentTarget);

    };

    const handleClose=()=>{

        setAnchorEl(null);

    };

    const notifications=[

        {

            title:"⚠️ High Priority",

            message:"2 High priority tasks pending"

        },

        {

            title:"📅 Due Today",

            message:"1 task is due today"

        },

        {

            title:"✅ Completed",

            message:"Project Documentation completed"

        }

    ];

    const [role, setRole] = useState(
        localStorage.getItem("role") || "USER"
    );

    useEffect(() => {

        const updateProfile = () => {

            setProfileImage(
                localStorage.getItem("profileImage") || ""
            );

            setName(
                localStorage.getItem("name") || "User"
            );

            setRole(
                localStorage.getItem("role") || "USER"
            );

        };

        updateProfile();

        window.addEventListener(
            "profileUpdated",
            updateProfile
        );

        window.addEventListener(
            "storage",
            updateProfile
        );

        return () => {

            window.removeEventListener(
                "profileUpdated",
                updateProfile
            );

            window.removeEventListener(
                "storage",
                updateProfile
            );

        };

    }, []);

    const logout = () => {

        localStorage.clear();

        navigate("/login");

    };

    return (

        <AppBar
            position="fixed"
            elevation={0}
            className="navbar"
        >

            <Toolbar>

                <Typography
                    variant="h5"
                    className="navbar-title"
                    sx={{
                        flexGrow:1,
                        display:"flex",
                        alignItems:"center",
                        gap:1
                    }}
                >

                    🚀 DevPulse

                </Typography>

                <Box
                    className="navbar-actions"
                >

                    <Tooltip
                        title="Notifications"
                    >

                        <IconButton
                            className="notification-btn"
                            onClick={handleOpen}
                        >

                            <Badge
                                badgeContent={3}
                                color="error"
                            >

                                <NotificationsIcon />

                            </Badge>

                        </IconButton>

                    </Tooltip>

                    <Box
                        className="profile-box"
                        sx={{
                            userSelect:"none"
                        }}
                    >

                        <Avatar
                            src={profileImage}
                            imgProps={{
                                loading:"lazy"
                            }}
                            className="navbar-avatar"
                        >

                            {
                                !profileImage &&
                                name.charAt(0).toUpperCase()
                            }

                        </Avatar>

                        <Box>

                            <Typography
                                className="user-name"
                            >
                                {name}
                            </Typography>

                            <Typography
                                variant="caption"
                                sx={{
                                    color: "#64748b",
                                    display: "block",
                                    fontWeight: 600,
                                }}
                            >
                                {role==="USER" ? "Developer" : role}
                            </Typography>

                        </Box>

                    </Box>

                    <Tooltip
                        title="Logout"
                    >

                        <IconButton
                            className="logout-btn"
                            color="error"
                            onClick={logout}
                        >

                            <LogoutIcon />

                        </IconButton>

                        <NotificationMenu

                            anchorEl={anchorEl}

                            open={open}

                            onClose={handleClose}

                            notifications={notifications}

                        />

                    </Tooltip>

                </Box>

            </Toolbar>

        </AppBar>

    );

}

export default Navbar;