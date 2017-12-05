var webpack = require("webpack");

var nodeRoot = __dirname + "/node_modules",
    jsSourceDir = __dirname + "/src/main/resources/static",
    outputDir = __dirname + "/target/classes/static";

module.exports = {
    entry: {
        "app":                 jsSourceDir + "/app/app.ts"
    },
    output: {
        path: outputDir + "/app-resources/bundles/",
        publicPath: "/app-resources/bundles/",
        filename: "[name].bundle.js"
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                use: ["ts-loader"]
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"]
            },
            {
                test: /\.woff(2)?(\?v=\d+\.\d+\.\d+)?$/,
                use: ["url-loader?limit=10000&mimetype=application/font-woff"]
            },
            {
                test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
                use: ["url-loader?limit=10000&mimetype=application/octet-stream"]
            },
            {
                test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
                use: ["file-loader"]
            },
            {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                use: ["url-loader?limit=10000&mimetype=image/svg+xml"]
            },
            {
                test: /\.png(\?v=\d+\.\d+\.\d+)?$/,
                use: ["url-loader?limit=10000&mimetype=image/png"]
            }
        ]
    },
    resolve: {
        modules: [
            nodeRoot,
            __dirname
        ],
        extensions: [".ts", ".js", ".css"]
    },
    node: {
        fs: "empty"
    }
};
