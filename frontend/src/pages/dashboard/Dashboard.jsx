import { useEffect, useState } from "react";

import {
    Container,
    Grid,
    Typography,
    Box,
    Paper,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    Chip,
} from "@mui/material";

import { getRecentTasks } from "../../services/taskService";

import AssignmentIcon from "@mui/icons-material/Assignment";
import PendingActionsIcon from "@mui/icons-material/PendingActions";
import AutorenewIcon from "@mui/icons-material/Autorenew";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import PriorityHighIcon from "@mui/icons-material/PriorityHigh";
import TodayIcon from "@mui/icons-material/Today";

import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    ResponsiveContainer,
} from "recharts";

import MainLayout from "../../components/layout/MainLayout";
import StatCard from "../../components/dashboard/StatCard";
import { getDashboard } from "../../services/dashboardService";
import "./Dashboard.css";
function Dashboard() {

    const [dashboard, setDashboard] = useState({
        totalTasks: 0,
        todo: 0,
        inProgress: 0,
        completed: 0,
        highPriority: 0,
        todayTasks: 0,
    });



    const [recentTasks, setRecentTasks] = useState([]);

    useEffect(() => {
        loadDashboard();
    }, []);


    const loadDashboard = async () => {

        try {

            const dashboardData = await getDashboard();

            setDashboard(dashboardData);

            const tasks = await getRecentTasks();

            setRecentTasks(tasks);

        } catch (e) {

            console.log(e);

        }

    };

    const hour = new Date().getHours();

    const greeting =
        hour < 12
            ? "Good Morning ☀️"
            : hour < 17
                ? "Good Afternoon 🌤️"
                : "Good Evening 🌙";

    const chartData = [
        {
            name: "Todo",
            value: dashboard.todo,
        },
        {
            name: "In Progress",
            value: dashboard.inProgress,
        },
        {
            name: "Completed",
            value: dashboard.completed,
        },
    ];

    const COLORS = [
        "#ff9800",
        "#2196f3",
        "#4caf50",
    ];

    return (

        <MainLayout>

            <Container maxWidth="xl">
                <Typography
                    className="dashboard-title"
                    variant="h4"
                    fontWeight="bold"
                    mb={1}
                >
                    {greeting}
                </Typography>

                <Typography
                    className="dashboard-subtitle"
                    mb={4}
                >
                    Welcome back to DevPulse 🚀
                </Typography>

                <Grid container spacing={3}>

                    <Grid size={{ xs: 12, md: 4 }}>
                        <StatCard
                            title="Total Tasks"
                            value={dashboard.totalTasks}
                            icon={<AssignmentIcon fontSize="large" />}
                            color="linear-gradient(135deg,#1976d2,#42a5f5)"
                        />
                    </Grid>

                    <Grid size={{ xs: 12, md: 4 }}>
                        <StatCard
                            title="Todo"
                            value={dashboard.todo}
                            icon={<PendingActionsIcon fontSize="large" />}
                            color="linear-gradient(135deg,#fb8c00,#ffb74d)"
                        />
                    </Grid>

                    <Grid size={{ xs: 12, md: 4 }}>
                        <StatCard
                            title="In Progress"
                            value={dashboard.inProgress}
                            icon={<AutorenewIcon fontSize="large" />}
                            color="linear-gradient(135deg,#7b1fa2,#ba68c8)"
                        />
                    </Grid>

                    <Grid size={{ xs: 12, md: 4 }}>
                        <StatCard
                            title="Completed"
                            value={dashboard.completed}
                            icon={<CheckCircleIcon fontSize="large" />}
                            color="linear-gradient(135deg,#2e7d32,#66bb6a)"
                        />
                    </Grid>

                    <Grid size={{ xs: 12, md: 4 }}>
                        <StatCard
                            title="High Priority"
                            value={dashboard.highPriority}
                            icon={<PriorityHighIcon fontSize="large" />}
                            color="linear-gradient(135deg,#c62828,#ef5350)"
                        />
                    </Grid>

                    <Grid size={{ xs: 12, md: 4 }}>
                        <StatCard
                            title="Today's Tasks"
                            value={dashboard.todayTasks}
                            icon={<TodayIcon fontSize="large" />}
                            color="linear-gradient(135deg,#00897b,#4db6ac)"
                        />
                    </Grid>

                </Grid>

                <Paper
                    className="chart-card"
                    elevation={3}
                    sx={{
                        mt: 5,
                        p: 3,
                        borderRadius: 4,
                    }}
                >

                    <Typography
                        variant="h6"
                        mb={2}
                    >
                        Task Distribution
                    </Typography>

                    <Box
                        sx={{
                            width: "100%",
                            height: 350,
                        }}
                    >

                        <ResponsiveContainer width="100%" height="100%">

                            <PieChart>

                                <Pie
                                    data={chartData}
                                    dataKey="value"
                                    outerRadius={120}
                                    label
                                >

                                    {chartData.map((entry, index) => (

                                        <Cell
                                            key={index}
                                            fill={COLORS[index]}
                                        />

                                    ))}

                                </Pie>

                                <Tooltip />

                            </PieChart>

                        </ResponsiveContainer>

                    </Box>

                </Paper>

                <Paper
                    className="recent-card"
                    elevation={3}
                    sx={{
                        mt: 4,
                        p: 3,
                        borderRadius: 4,
                    }}
                >

                    <Typography
                        variant="h6"
                        mb={2}
                    >
                        Recent Tasks
                    </Typography>

                    <Table className="dashboard-table">

                        <TableHead>

                            <TableRow>

                                <TableCell>Title</TableCell>

                                <TableCell>Status</TableCell>

                                <TableCell>Priority</TableCell>

                                <TableCell>Due Date</TableCell>

                            </TableRow>

                        </TableHead>

                        <TableBody>

                            {recentTasks.map((task) => (

                                <TableRow key={task.id}>

                                    <TableCell>
                                        {task.title}
                                    </TableCell>

                                    <TableCell>

                                        <Chip
                                            label={task.status}
                                            color={
                                                task.status === "COMPLETED"
                                                    ? "success"
                                                    : task.status === "IN_PROGRESS"
                                                        ? "info"
                                                        : "warning"
                                            }
                                            size="small"
                                        />

                                    </TableCell>

                                    <TableCell>

                                        <Chip
                                            label={task.priority || "None"}
                                            color={
                                                task.priority === "HIGH"
                                                    ? "error"
                                                    : task.priority === "MEDIUM"
                                                        ? "warning"
                                                        : "success"
                                            }
                                            size="small"
                                        />

                                    </TableCell>

                                    <TableCell>
                                        {task.dueDate || "-"}
                                    </TableCell>

                                </TableRow>

                            ))}

                        </TableBody>

                    </Table>

                </Paper>

            </Container>

        </MainLayout>

    );

}

export default Dashboard;