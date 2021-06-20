package com.alphabet.abstractor.utils;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ReplacerMethod extends MethodVisitor {

    private final Type returnType;
    private final MethodVisitor targetWriter;

    ReplacerMethod(Type returnType, MethodVisitor writer) {
        super(Opcodes.ASM5);
        this.returnType = returnType;
        this.targetWriter = writer;
    }

    @Override
    public void visitCode() {
        targetWriter.visitCode();

        if (returnType == Type.VOID_TYPE)
            targetWriter.visitInsn(Opcodes.RETURN);

        else {
            targetWriter.visitInsn(Opcodes.ACONST_NULL);
            targetWriter.visitInsn(Opcodes.ARETURN);
        }
    }
}
