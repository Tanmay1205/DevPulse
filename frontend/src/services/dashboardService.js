import axiosInstance from "../api/axiosConfig";

export const getDashboard = async () => {
    const response = await axiosInstance.get("/dashboard");
    return response.data;
};

export const getActivity = async () => {

    const response = await axiosInstance.get("/dashboard/activity");

    return response.data;

};

export const getRecentTasks = async () => {

    const response = await axiosInstance.get("/dashboard/recent-tasks");

    return response.data;

};

