package com.alphabet.abstractor.utils;

import org.objectweb.asm.*;

public class MethodModifier extends ClassVisitor {

    public boolean shouldIgnore;

    MethodModifier(ClassWriter writer) {
        super(Opcodes.ASM5, writer);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if ((access & Opcodes.ACC_PUBLIC) == 0)
            shouldIgnore = true;
        else
            super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if ((access & Opcodes.ACC_PUBLIC) == 0)
            return null;

        return new ReplacerMethod(
                Type.getReturnType(desc),
                super.visitMethod(access, name, desc, signature, exceptions));
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if ((access & Opcodes.ACC_PUBLIC) == 0)
            return null;

        return super.visitField(access, name, desc, signature, value);
    }

    public byte[] toByteArray() {
        return ((ClassWriter) this.cv).toByteArray();
    }
}