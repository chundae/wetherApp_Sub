import {createProxyMiddleware, RequestHandler} from "http-proxy-middleware";
import {Express} from 'express-serve-static-core';

const proxy: RequestHandler = createProxyMiddleware({
    target:"http://localhost:8080",
    changeOrigin: true,
});

export default function (app: Express) {
    app.use('/proxy', proxy);
};