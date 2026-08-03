import { useEffect, useState } from "react";
import {
    Grid,
    Paper,
    Typography,
    Card,
    CardContent,
    Box,
} from "@mui/material";

import MainLayout from "../../components/layout/MainLayout";
import { getTasks } from "../../services/taskService";

function KanbanBoard() {

    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        loadTasks();
    }, []);

    const loadTasks = async () => {

        try {

            const response = await getTasks();

            setTasks(response.content);

        } catch (e) {

            console.log(e);

        }

    };

    const todo = tasks.filter(
        (task) => task.status === "TODO"
    );

    const progress = tasks.filter(
        (task) => task.status === "IN_PROGRESS"
    );

    const completed = tasks.filter(
        (task) => task.status === "COMPLETED"
    );

    const Column = ({ title, items, color }) => (

        <Paper
            elevation={3}
            sx={{
                p: 2,
                minHeight: "75vh",
                borderTop: `6px solid ${color}`,
            }}
        >

            <Typography
                variant="h6"
                fontWeight="bold"
                mb={2}
            >
                {title}
            </Typography>

            {items.map((task) => (

                <Card
                    key={task.id}
                    sx={{
                        mb: 2,
                        cursor: "grab",
                    }}
                >

                    <CardContent>

                        <Typography fontWeight="bold">

                            {task.title}

                        </Typography>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >

                            {task.description}

                        </Typography>

                    </CardContent>

                </Card>

            ))}

        </Paper>

    );

    return (

        <MainLayout>

            <Typography
                variant="h4"
                fontWeight="bold"
                mb={3}
            >
                Kanban Board
            </Typography>

            <Grid container spacing={3}>

                <Grid size={{ xs:12, md:4 }}>

                    <Column
                        title="TODO"
                        items={todo}
                        color="#ff9800"
                    />

                </Grid>

                <Grid size={{ xs:12, md:4 }}>

                    <Column
                        title="IN PROGRESS"
                        items={progress}
                        color="#2196f3"
                    />

                </Grid>

                <Grid size={{ xs:12, md:4 }}>

                    <Column
                        title="COMPLETED"
                        items={completed}
                        color="#4caf50"
                    />

                </Grid>

            </Grid>

        </MainLayout>

    );

}

export default KanbanBoard;