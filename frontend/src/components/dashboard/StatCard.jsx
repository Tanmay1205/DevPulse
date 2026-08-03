import { Card, CardContent, Typography, Box } from "@mui/material";

function StatCard({ title, value, icon, color }) {
    return (
        <Card
            sx={{
                borderRadius: 4,
                background: color,
                color: "white",
                boxShadow: 4,
                transition: "0.3s",
                "&:hover": {
                    transform: "translateY(-5px)",
                },
            }}
        >
            <CardContent>
                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                    }}
                >
                    <Box>
                        <Typography variant="body1">
                            {title}
                        </Typography>

                        <Typography
                            variant="h4"
                            fontWeight="bold"
                        >
                            {value}
                        </Typography>
                    </Box>

                    <Box
                        sx={{
                            fontSize: 40,
                        }}
                    >
                        {icon}
                    </Box>
                </Box>
            </CardContent>
        </Card>
    );
}

export default StatCard;