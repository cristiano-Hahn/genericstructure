CREATE TABLE company (
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     name TEXT NOT NULL,
     enabled BOOLEAN NOT NULL,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
     address TEXT,
     document_number TEXT,
     document_type TEXT
);