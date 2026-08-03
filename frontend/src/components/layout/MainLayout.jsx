import { Box } from "@mui/material";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";

function MainLayout({ children }) {
    return (
        <>
            <Navbar />

            <Box sx={{ display: "flex" }}>
                <Sidebar />

                <Box
                    component="main"
                    sx={{
                        flexGrow: 1,
                        ml: "230px",
                        mt: "64px",
                        p: 3,
                        minHeight: "calc(100vh - 64px)",
                        backgroundColor: "#f5f5f5",
                        overflow: "auto",
                    }}
                >
                    {children}
                </Box>
            </Box>
        </>
    );
}

export default MainLayout;