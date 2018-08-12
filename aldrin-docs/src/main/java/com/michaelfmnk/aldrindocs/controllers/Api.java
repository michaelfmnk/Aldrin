package com.michaelfmnk.aldrindocs.controllers;

public final class Api {
    private Api() {
    }

    public static final String ROOT_PATH = "/docs-api";

    public static final class TemporaryStorage {
        private TemporaryStorage() {
        }

        public static final String TEMPORARY_LOCATION = "/temporary";
        public static final String TEMPORARY_FILE_BY_ID = "/temporary/{file_id}";
    }

    public static final class PermanentStorage {
        private PermanentStorage() {
        }

        public static final String PERMANENT_LOCATION = "/permanent";
        public static final String PERMANENT_FILE_BY_ID = "/permanent/{file_id}";
    }

    public static final class Common {
        private Common() {
        }

        public static final String GIT_LOG = "/gitlog";
    }
}
