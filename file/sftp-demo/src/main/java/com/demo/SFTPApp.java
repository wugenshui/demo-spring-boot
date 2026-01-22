package com.demo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author : chenbo
 * @date : 2026-01-21
 */
public class SFTPApp {
    public static void main(String[] args) {
        // 默认值（可用于开发测试）
        String host = "192.168.3.25";
        int port = 2222;
        String username = "admin";
        String password = "admin";

        // 操作相关参数
        String operation = "list";        // list | download | upload
        String remotePath = "/";          // list / download 的远程路径
        String localDir = "/tmp";         // download 保存的本地目录
        String localFile = null;          // upload 的本地文件
        String remoteDir = "/";           // upload 的远程目录
        boolean recursive = false;         // list / download 是否递归

        // 简单参数解析
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "--host":
                    host = args[++i];
                    break;
                case "-P":
                case "--port":
                    port = Integer.parseInt(args[++i]);
                    break;
                case "-u":
                case "--user":
                    username = args[++i];
                    break;
                case "-pwd":
                case "--password":
                    password = args[++i];
                    break;
                case "-op":
                case "--operation":
                    operation = args[++i].toLowerCase();
                    break;
                case "-remote":
                case "--remotePath":
                    remotePath = args[++i];
                    break;
                case "-localDir":
                    localDir = args[++i];
                    break;
                case "-localFile":
                    localFile = args[++i];
                    break;
                case "-remoteDir":
                    remoteDir = args[++i];
                    break;
                case "-r":
                case "--recursive":
                    recursive = true;
                    break;
                case "--help":
                case "-?":
                    printUsage();
                    return;
                default:
                    System.out.println("未知参数: " + arg);
                    printUsage();
                    return;
            }
        }

        // 基本参数校验
        if (!operation.equals("list") && !operation.equals("download") && !operation.equals("upload")) {
            System.out.println("❌ 不支持的操作类型: " + operation);
            printUsage();
            return;
        }

        if ("download".equals(operation)) {
            if (remotePath == null || remotePath.isEmpty()) {
                System.out.println("❌ download 需要指定远程路径: -remote <remotePath>");
                printUsage();
                return;
            }
        }

        if ("upload".equals(operation)) {
            if (localFile == null || localFile.isEmpty()) {
                System.out.println("❌ upload 需要指定本地文件: -localFile <filePath>");
                printUsage();
                return;
            }
        }

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            System.out.println("✅ Connected to SFTP server: " + host + ":" + port);

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

            switch (operation) {
                case "list":
                    System.out.println("\n🔍 开始遍历远程目录: " + remotePath + " (recursive=" + recursive + ")");
                    listDirectory(sftpChannel, remotePath, recursive, "");
                    break;
                case "download":
                    System.out.println("\n📥 开始下载: " + remotePath + " → 本地目录: " + localDir + " (recursive=" + recursive + ")");
                    downloadPath(sftpChannel, remotePath, new File(localDir), recursive);
                    System.out.println("✅ 下载完成");
                    break;
                case "upload":
                    System.out.println("\n📤 开始上传: " + localFile + " → 远程目录: " + remoteDir);
                    uploadFile(sftpChannel, new File(localFile), remoteDir);
                    System.out.println("✅ 上传完成");
                    break;
                default:
                    System.out.println("❌ 未知操作: " + operation);
                    printUsage();
            }

        } catch (Exception e) {
            System.err.println("💥 Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sftpChannel != null && sftpChannel.isConnected()) sftpChannel.disconnect();
            if (session != null && session.isConnected()) session.disconnect();
        }
    }

    /**
     * java -jar sftp-demo-0.0.1.jar -h 192.168.3.25 -P 2222 -u admin -pwd admin -op list -remote /
     * java -jar sftp-demo-0.0.1.jar -h 192.168.3.25 -P 2222 -u admin -pwd admin -op download -remote 指定的文件 -localDir /tmp/sftp.data
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar sftp-app.jar [OPTIONS]");
        System.out.println();
        System.out.println("必选参数:");
        System.out.println("  -op, --operation <op>    操作类型: list | download | upload   (默认: list)");
        System.out.println();
        System.out.println("通用连接参数:");
        System.out.println("  -h,  --host <host>       SFTP 服务器地址 (默认: 192.168.3.25)");
        System.out.println("  -P,  --port <port>       端口 (默认: 2222)");
        System.out.println("  -u,  --user <user>       用户名 (默认: admin)");
        System.out.println("  -pwd,--password <pwd>    密码 (默认: admin)");
        System.out.println();
        System.out.println("[list] 遍历远程目录:");
        System.out.println("  -op list -remote <path> [-r]");
        System.out.println("    -remote, --remotePath  要遍历的远程目录 (默认: /)");
        System.out.println("    -r, --recursive        是否递归子目录 (默认不递归)");
        System.out.println();
        System.out.println("[download] 下载文件或目录:");
        System.out.println("  -op download -remote <path> -localDir <localDir> [-r]");
        System.out.println("    -remote, --remotePath  远程文件或目录路径");
        System.out.println("    -localDir              本地保存目录 (默认: /tmp)");
        System.out.println("    -r, --recursive        若 remote 是目录, 是否递归下载子目录 (默认不递归)");
        System.out.println();
        System.out.println("[upload] 上传单个文件:");
        System.out.println("  -op upload -localFile <file> -remoteDir <remoteDir>");
        System.out.println("    -localFile             本地待上传文件路径");
        System.out.println("    -remoteDir             远程保存目录 (默认: /)");
        System.out.println();
        System.out.println("其他:");
        System.out.println("  --help, -?               显示本帮助");
    }

    /**
     * 递归/非递归遍历远程目录
     */
    public static void listDirectory(ChannelSftp sftpChannel, String remotePath, boolean recursive, String indent) {
        try {
            Vector<ChannelSftp.LsEntry> entries = sftpChannel.ls(remotePath);
            if (entries == null || entries.isEmpty()) {
                System.out.println(indent + "(空目录) " + remotePath);
                return;
            }

            for (ChannelSftp.LsEntry entry : entries) {
                String filename = entry.getFilename();
                if (".".equals(filename) || "..".equals(filename)) {
                    continue;
                }

                String fullPath = remotePath + (remotePath.endsWith("/") ? "" : "/") + filename;
                if (entry.getAttrs().isDir()) {
                    System.out.println(indent + "[DIR]  " + fullPath);
                    if (recursive) {
                        listDirectory(sftpChannel, fullPath, true, indent + "  ");
                    }
                } else {
                    System.out.println(indent + "[FILE] " + fullPath + " (" + entry.getAttrs().getSize() + " bytes)");
                }
            }
        } catch (SftpException e) {
            System.err.println("Error listing directory: " + remotePath + " - " + e.getMessage());
        }
    }

    /**
     * 根据远程路径下载文件或目录
     */
    public static void downloadPath(ChannelSftp sftpChannel, String remotePath, java.io.File localDir, boolean recursive) throws SftpException {
        try {
            if (!localDir.exists() && !localDir.mkdirs()) {
                throw new RuntimeException("无法创建本地目录: " + localDir.getAbsolutePath());
            }

            SftpException statEx = null;
            Vector<ChannelSftp.LsEntry> entries;
            try {
                // 尝试当作单个文件下载
                java.io.File target = new java.io.File(localDir, new java.io.File(remotePath).getName());
                sftpChannel.get(remotePath, new FileOutputStream(target));
                System.out.println("  已下载文件: " + remotePath);
                return;
            } catch (SftpException e) {
                statEx = e;
            }

            // 如果不是文件, 当作目录处理
            try {
                entries = sftpChannel.ls(remotePath);
            } catch (SftpException e) {
                throw new SftpException(e.id, "既不是文件也不是目录: " + remotePath + ", 原因: " + (statEx != null ? statEx.getMessage() : e.getMessage()));
            }

            if (entries == null || entries.isEmpty()) {
                System.out.println("  远程目录为空: " + remotePath);
                return;
            }

            for (ChannelSftp.LsEntry entry : entries) {
                String filename = entry.getFilename();
                if (".".equals(filename) || "..".equals(filename)) {
                    continue;
                }
                String childRemote = remotePath + (remotePath.endsWith("/") ? "" : "/") + filename;
                if (entry.getAttrs().isDir()) {
                    if (recursive) {
                        java.io.File childLocalDir = new java.io.File(localDir, filename);
                        System.out.println("  [DIR]  " + childRemote);
                        downloadPath(sftpChannel, childRemote, childLocalDir, true);
                    } else {
                        System.out.println("  跳过子目录(未开启递归): " + childRemote);
                    }
                } else {
                    java.io.File target = new java.io.File(localDir, filename);
                    System.out.println("  [FILE] 下载: " + childRemote + " → " + target.getAbsolutePath());
                    sftpChannel.get(childRemote, new FileOutputStream(target));
                }
            }
        } catch (SftpException e) {
            throw e;
        } catch (Exception e) {
            throw new SftpException(0, "下载失败: " + remotePath + " - " + e.getMessage(), e);
        }
    }

    /**
     * 上传单个文件到远程目录
     */
    public static void uploadFile(ChannelSftp sftpChannel, java.io.File localFile, String remoteDir) throws SftpException {
        if (!localFile.exists() || !localFile.isFile()) {
            throw new SftpException(0, "本地文件不存在或不是文件: " + localFile.getAbsolutePath());
        }

        String remotePath = remoteDir + (remoteDir.endsWith("/") ? "" : "/") + localFile.getName();
        try (FileInputStream fis = new FileInputStream(localFile)) {
            sftpChannel.put(fis, remotePath);
            System.out.println("  已上传文件: " + localFile.getAbsolutePath() + " → " + remotePath);
        } catch (Exception e) {
            throw new SftpException(0, "上传失败: " + localFile.getAbsolutePath() + " - " + e.getMessage(), e);
        }
    }

}
