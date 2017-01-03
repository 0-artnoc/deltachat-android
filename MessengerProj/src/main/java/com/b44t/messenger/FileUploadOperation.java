/*******************************************************************************
 *
 *                          Messenger Android Frontend
 *                        (C) 2013-2016 Nikolai Kudashov
 *                           (C) 2017 Björn Petersen
 *                    Contact: r10s@b44t.com, http://b44t.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see http://www.gnu.org/licenses/ .
 *
 ******************************************************************************/


package com.b44t.messenger;

import java.io.FileInputStream;

public class FileUploadOperation {

    public int state = 0;
    public FileUploadOperationDelegate delegate;
    private int requestToken = 0;
    private long totalFileSize = 0;
    private boolean isEncrypted = false;
    private int estimatedSize = 0;
    private FileInputStream stream;

    public interface FileUploadOperationDelegate {
        void didFinishUploadingFile(FileUploadOperation operation, TLRPC.InputFile inputFile, TLRPC.InputEncryptedFile inputEncryptedFile, byte[] key, byte[] iv);
        void didFailedUploadingFile(FileUploadOperation operation);
        void didChangedUploadProgress(FileUploadOperation operation, float progress);
    }

    public FileUploadOperation(String location, boolean encrypted, int estimated) {
        isEncrypted = encrypted;
        estimatedSize = estimated;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void start() {
    }

    public void cancel() {
        if (state == 3) {
            return;
        }
        state = 2;
        if (requestToken != 0) {
            ConnectionsManager.getInstance().cancelRequest(requestToken, true);
        }
        delegate.didFailedUploadingFile(this);
        cleanup();
    }

    private void cleanup() {
    }

    /*
    protected void checkNewDataAvailable(final long finalSize) {
        Utilities.stageQueue.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (estimatedSize != 0 && finalSize != 0) {
                    estimatedSize = 0;
                    totalFileSize = finalSize;
                    totalPartsCount = (int) (totalFileSize + uploadChunkSize - 1) / uploadChunkSize;
                    if (started) {
                        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", Activity.MODE_PRIVATE);
                        storeFileUploadInfo(preferences);
                    }
                }
                if (requestToken == 0) {
                    startUploadRequest();
                }
            }
        });
    }
    */


}
