import axiosInstance from "../api/axiosConfig";

export const login = async (data) => {
    const response = await axiosInstance.post("/auth/login", data);
    return response.data;
};

export const register = async (data) => {
    const response = await axiosInstance.post("/auth/register", data);
    return response.data;
};

export const forgotPassword = async (data) => {

    const response = await axiosInstance.post(
        "/auth/forgot-password",
        data
    );

    return response.data;

};

export const resetPassword = async (data) => {

    const response = await axiosInstance.post(

        "/auth/reset-password",

        data

    );

    return response.data;

};