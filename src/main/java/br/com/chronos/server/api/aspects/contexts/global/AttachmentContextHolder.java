
package br.com.chronos.server.api.aspects.contexts.global;

import br.com.chronos.core.global.domain.dtos.AttachmentDto;

public class AttachmentContextHolder {
    private static final ThreadLocal<AttachmentDto> context = new ThreadLocal<>();

    public static void set(AttachmentDto dto) {
        context.set(dto);
    }

    public static AttachmentDto get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
