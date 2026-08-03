import {
    Drawer,
    List,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Divider,
} from "@mui/material";


import "./Sidebar.css";
import DashboardIcon from "@mui/icons-material/Dashboard";
import TaskIcon from "@mui/icons-material/Task";
import PersonIcon from "@mui/icons-material/Person";
import LogoutIcon from "@mui/icons-material/Logout";
import ViewKanbanIcon from "@mui/icons-material/ViewKanban";

import { Link, useLocation, useNavigate } from "react-router-dom";

const drawerWidth = 230;

function Sidebar() {

    const location = useLocation();
    const navigate = useNavigate();

    const logout = () => {

        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("name");
        localStorage.removeItem("role");

        navigate("/login");

    };

    const menu = [
        {
            text: "Dashboard",
            path: "/dashboard",
            icon: <DashboardIcon />,
        },
        {
            text: "Tasks",
            path: "/tasks",
            icon: <TaskIcon />,
        },
        {
            text: "Kanban",
            path: "/kanban",
            icon: <ViewKanbanIcon />,
        },
        {
            text: "Profile",
            path: "/profile",
            icon: <PersonIcon />,
        },
    ];

    return (
        <Drawer
            variant="permanent"
            className="sidebar"
            sx={{
                width: drawerWidth,
                flexShrink: 0,

                "& .MuiDrawer-paper": {

                    width: drawerWidth,

                    boxSizing: "border-box",

                    top: "64px",

                    height: "calc(100vh - 64px)",

                },

            }}
        >
            <List sx={{ mt: 1 }}>
                {menu.map((item) => (

                    <ListItemButton
                        className="sidebar-item"
                        key={item.text}
                        component={Link}
                        to={item.path}
                        selected={location.pathname === item.path}
                        sx={{
                            mx: 1,
                            mb: 0.5,
                            borderRadius: 2,

                            "&.Mui-selected": {
                                backgroundColor: "#1976d2",
                                color: "#fff",
                            },

                            "&.Mui-selected .MuiListItemIcon-root": {
                                color: "#fff",
                            },

                            "&:hover": {
                                backgroundColor: "#e3f2fd",
                            },
                        }}
                    >
                        <ListItemIcon>
                            {item.icon}
                        </ListItemIcon>

                        <ListItemText primary={item.text} />
                    </ListItemButton>

                ))}

                <Divider className="sidebar-divider"/>

                <ListItemButton
                    className="logout-item"
                    onClick={logout}
                    sx={{
                        mx: 1,
                        borderRadius: 2,
                    }}
                >
                    <ListItemIcon>
                        <LogoutIcon />
                    </ListItemIcon>

                    <ListItemText primary="Logout" />
                </ListItemButton>

            </List>
        </Drawer>
    );
}

export default Sidebar;